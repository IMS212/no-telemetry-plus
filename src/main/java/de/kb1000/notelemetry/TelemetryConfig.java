package de.kb1000.notelemetry;

import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.render.ChunkBuilderMode;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * A class dedicated to storing the config values of telemetry.
 */
public class TelemetryConfig {
    private static final String COMMENT =
            "This file stores configuration options for No Telemetry Plus.";

    public static TelemetryConfig instance;

    private TelemetryOptions telemetryOption;

    private final Path propertiesPath;

    public static void createInstance(Path propertiesPath) {
        instance = new TelemetryConfig(propertiesPath);
        try {
            instance.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TelemetryConfig(Path propertiesPath) {
        telemetryOption = TelemetryOptions.ANONYMOUS;
        this.propertiesPath = propertiesPath;
    }

    /**
     * Initializes the configuration, loading it if it is present and creating a default config otherwise.
     *
     * @throws IOException file exceptions
     */
    public void initialize() throws IOException {
        load();
        if (!Files.exists(propertiesPath)) {
            save();
        }
    }

    public TelemetryOptions getTelemetryOption() {
        return telemetryOption;
    }

    public void setTelemetryOption(TelemetryOptions telemetryOption) {
        this.telemetryOption = telemetryOption;
    }

    public static final CyclingOption<TelemetryOptions> TELEMETRY_OPTIONS_CYCLING_OPTION = CyclingOption.create(
            "Telemetry", // yes i know this is supposed to be translated but cope
            TelemetryOptions.values(),
            TelemetryOptions::getText,
            gameOptions -> instance.getTelemetryOption(),
            (gameOptions, option, telemetry) -> instance.setTelemetryOption(telemetry)
    ).tooltip(minecraftClient -> {
        return new CyclingButtonWidget.TooltipFactory<TelemetryOptions>() {
            @Override
            public List<OrderedText> apply(TelemetryOptions telemetryOptions) {
                ArrayList<OrderedText> list = new ArrayList<>();
                list.add(new LiteralText("Mojang sends telemetry info containing your XUID by default.").asOrderedText());
                list.add(new LiteralText("The \"Anonymous\" option removes your XUID and Client ID.").asOrderedText());
                list.add(new LiteralText("The \"Off\" option blocks all telemetry.").asOrderedText());
                list.add(new LiteralText("This may require a restart to take effect.").asOrderedText());
                return list;
            }
        };
    });

    /**
     * loads the config file and then populates the string, int, and boolean entries with the parsed entries
     *
     * @throws IOException if the file cannot be loaded
     */

    public void load() throws IOException, NumberFormatException {
        if (!Files.exists(propertiesPath)) {
            return;
        }

        Properties properties = new Properties();
        // NB: This uses ISO-8859-1 with unicode escapes as the encoding
        properties.load(Files.newInputStream(propertiesPath));
        telemetryOption = TelemetryOptions.fromId(Integer.parseInt(properties.getProperty("shaderPack")));
    }

    /**
     * Serializes the config into a file. Should be called whenever any config values are modified.
     *
     * @throws IOException file exceptions
     */
    public void save() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("telemetryOption", String.valueOf(telemetryOption.id));
        // NB: This uses ISO-8859-1 with unicode escapes as the encoding
        properties.store(Files.newOutputStream(propertiesPath), COMMENT);
    }
}
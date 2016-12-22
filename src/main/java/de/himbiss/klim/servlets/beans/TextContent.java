package de.himbiss.klim.servlets.beans;

/**
 * Created by Vincent on 17.12.2016.
 */
public class TextContent implements Content {
    @Override
    public String getHTML() {
        return "<h1>Kommi123</h1>";
    }
}

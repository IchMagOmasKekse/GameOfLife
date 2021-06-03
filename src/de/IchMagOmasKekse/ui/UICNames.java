package de.IchMagOmasKekse.ui;

public enum UICNames {

    UIC_CANVAS("UIC-Canvas"),
    UIC_TEXT("UIC-Text"),
    UIC_BUTTON("UIC-Button"),
    UIC_SLIDER("UIC-Slider"),
    UIC_ITEM_LIST("UIC-Item-List"),
    UIC_GAMESTATE("UIC-GameState-Display");

    private String systemName = "";

    UICNames(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }
}

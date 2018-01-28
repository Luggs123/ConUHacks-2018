import javafx.scene.paint.Color;
public enum Tone {
    None,
    Anger,
    Disgust,
    Fear,
    Joy,
    Sadness,
    Analytical,
    Confident,
    Tentative;
    
    // TODO:
    public static Color getToneColor(Tone tone) {
        switch (tone) {
        case None: return Color.web("0xFFFFFF", 1);
        case Anger: return Color.web("0xFF6060", 1);
        case Disgust: return Color.web("0x60FF60", 1);
        case Fear: return Color.web("0xFFA0FF", 1);
        case Joy: return Color.web("0xFFFF80", 1);
        case Sadness: return Color.web("0x6060FF", 1);
        case Analytical: return Color.web("0x00FF00", 1);
        case Confident: return Color.web("0xFF0000", 1);
        case Tentative: return Color.web("0x0000FF", 1);
        default: return Color.web("0xFFFFFF", 1);
        }
    }
}

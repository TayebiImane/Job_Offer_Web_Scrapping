
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

class TextAreaOutputStream extends OutputStream {
    private JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void write(int b) throws IOException {
        // Append the text to the text area
        // Ensure the update happens on the Swing UI thread
        SwingUtilities.invokeLater(() -> {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}

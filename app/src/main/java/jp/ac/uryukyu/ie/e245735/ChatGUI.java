package jp.ac.uryukyu.ie.e245735;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.kherud.llama.LlamaOutput;

public class ChatGUI extends JFrame {
    private JLabel chatLabel;
    private JTextField inputField;
    private JButton sendButton;
    private ChatTemplate chatTemplate;
    private LLM llm;

    public ChatGUI(ChatTemplate chatTemplate, LLM llm) {
        this.chatTemplate = chatTemplate;
        this.llm = llm;

        setTitle("MemoLLM");
        
        chatLabel = new JLabel("<html></html>");
        inputField = new JTextField(30);
        sendButton = new JButton("Send");
        
        setupUI();
        
        sendButton.addActionListener(e -> handleSendMessage());
    }

    private void setupUI() {
        JScrollPane chatScrollPane = new JScrollPane(chatLabel);
        chatScrollPane.setPreferredSize(new Dimension(500,400));

        JPanel chatPanel = new JPanel();
        chatPanel.add(inputField);
        chatPanel.add(sendButton);

        JPanel chatContainer = new JPanel(new BorderLayout());
        chatContainer.add(BorderLayout.CENTER, chatScrollPane);
        chatContainer.add(BorderLayout.SOUTH, chatPanel);

        add(chatContainer);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void appendUserChat(String input) {
        chatTemplate.addUserChat(input);
        chatLabel.setText("<html>" + chatTemplate.getChatHistoryForUser().replaceAll("\n", "<br>") + "</html>");
    }

    private String getLLMResponse(String input) {
        String response = "";
        for (LlamaOutput output : llm.generate(input)) {
            response += output;
        }
        return response;
    }

    private void handleSendMessage() {
        String userInput = inputField.getText();
        if (!userInput.isEmpty()) {
            chatTemplate.addUserChat(userInput);
            chatLabel.setText("<html>" + chatTemplate.getChatHistoryForUser().replaceAll("\n", "<br>") + "</html>");
            String response = getLLMResponse(chatTemplate.convertToInput());
            chatTemplate.addAssistantChat(response);
            chatLabel.setText("<html>" + chatTemplate.getChatHistoryForUser().replaceAll("\n", "<br>") + "</html>");
            inputField.setText("");
        }
    }
}

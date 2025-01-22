package jp.ac.uryukyu.ie.e245735;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.kherud.llama.LlamaOutput;
import jp.ac.uryukyu.ie.e245735.params.MemoParams;

public class ChatGUI extends JFrame {
    private JLabel chatLabel;
    private JTextField inputField;
    private JButton sendButton;
    private JTextArea memoArea;
    private ChatTemplate chatTemplate;
    private LLM llm;
    private MemoParams memoParams;

    public ChatGUI(ChatTemplate chatTemplate, LLM llm, MemoParams memoParams) {
        this.chatTemplate = chatTemplate;
        this.llm = llm;
        this.memoParams = memoParams;

        setTitle("MemoLLM");
        
        chatLabel = new JLabel("<html></html>");
        inputField = new JTextField(30);
        sendButton = new JButton("Send");
        
        setupUI();
        
        sendButton.addActionListener(e -> handleSendMessage());
    }

    private void setupUI() {
        JScrollPane chatScrollPane = new JScrollPane(chatLabel);
        chatScrollPane.setPreferredSize(new Dimension(400,300));

        JPanel chatPanel = new JPanel();
        chatPanel.add(inputField);
        chatPanel.add(sendButton);

        JPanel chatContainer = new JPanel(new BorderLayout());
        chatContainer.add(BorderLayout.CENTER, chatScrollPane);
        chatContainer.add(BorderLayout.SOUTH, chatPanel);

        memoArea = new JTextArea();
        JScrollPane memoScrollPane = new JScrollPane(memoArea);
        memoScrollPane.setPreferredSize(new Dimension(200,300));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMemo());

        JPanel memoPanel = new JPanel();
        memoPanel.add(BorderLayout.CENTER, memoScrollPane);
        memoPanel.add(BorderLayout.SOUTH, saveButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, memoPanel, chatContainer);
        splitPane.setDividerLocation(200);

        getContentPane().add(splitPane);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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

    private void saveMemo() {
        String memoText = memoArea.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(memoParams.getFilePath()))) {
            writer.write(memoText);
            JOptionPane.showMessageDialog(this, "Saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save.");
        }
    }
}

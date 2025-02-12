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
import javax.swing.BorderFactory;

import de.kherud.llama.LlamaOutput;
import jp.ac.uryukyu.ie.e245735.params.MemoParameters;

/**
 * ChatGUI
 * 
 * チャット画面を表示するクラス
 * 
 * @param chatLabel チャット履歴を表示するラベル
 * @param inputField ユーザーの入力を受け付けるテキストフィールド
 * @param sendButton メッセージ送信ボタン
 * @param memoArea メモを表示するテキストエリア
 * @param chatTemplate チャットテンプレート
 * @param llm LLM
 * @param memoParams メモのパラメータ
 */
public class ChatGUI extends JFrame {
    private JLabel chatLabel;
    private JTextField inputField;
    private JButton sendButton;
    private JTextArea memoArea;
    private ChatTemplate chatTemplate;
    private Llm llm;
    private MemoParameters memoParams;

    public ChatGUI(ChatTemplate chatTemplate, Llm llm, MemoParameters memoParams) {
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

    /**
     * UIを設定する
     */
    private void setupUI() {
        JScrollPane chatScrollPane = new JScrollPane(chatLabel);
        chatScrollPane.setBorder(BorderFactory.createTitledBorder("Chat History"));
        chatScrollPane.setPreferredSize(new Dimension(600, 400));

        JPanel chatPanel = new JPanel();
        chatPanel.add(inputField);
        chatPanel.add(sendButton);

        JPanel chatContainer = new JPanel(new BorderLayout());
        chatContainer.add(BorderLayout.CENTER, chatScrollPane);
        chatContainer.add(BorderLayout.SOUTH, chatPanel);

        memoArea = new JTextArea();
        JScrollPane memoScrollPane = new JScrollPane(memoArea);
        memoScrollPane.setBorder(BorderFactory.createTitledBorder("Memo"));
        memoScrollPane.setPreferredSize(new Dimension(200,400));
        memoArea.setText(memoParams.getContents());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMemo());

        JPanel memoPanel = new JPanel();
        memoPanel.setLayout(new BorderLayout());
        memoPanel.add(memoScrollPane, BorderLayout.CENTER);
        memoPanel.add(saveButton, BorderLayout.SOUTH);

        inputField.setToolTipText("メッセージを入力してください...");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, memoPanel, chatContainer);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.3);

        getContentPane().add(splitPane);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * メッセージを送信する
     * 
     * ユーザーの入力を受け取り、LLMの出力を受け取ってチャット履歴に追加する
     * 
     * @param input ユーザーの入力
     * 
     * @return LLMの出力
     */
    private String getLLMResponse(String input) {
        String response = "";
        for (LlamaOutput output : llm.generate(input)) {
            response += output;
        }
        return response;
    }

    /**
     * メッセージを処理する
     * 
     * 入力フィールドからユーザーの入力を取得し、チャットを処理してから入力フィールドをクリアする
     * 
     * @param userInput ユーザーの入力
     */
    private void handleSendMessage() {
        String userInput = inputField.getText();
        if (!userInput.isEmpty()) {
            processUserChat(userInput);
            inputField.setText("");
        }
    }

    /**
     * ユーザーの入力を処理する
     * 
     * ユーザーの入力を受け取り、チャット履歴に追加する
     * 
     * @param userInput ユーザーの入力
     */
    private void processUserChat(String userInput) {
        chatTemplate.addUserChat(userInput);
        updateChatLabel();
        String structuredMemo = getLLMResponse(chatTemplate.getPromptForStructuringMemo(memoParams.getContents()));
        String searchResult = extractSearchResult(userInput, structuredMemo);
        String response = getLLMResponse(chatTemplate.convertToInput(searchResult));
        chatTemplate.addAssistantChat(response);
        updateChatLabel();
    }

    /**
     * チャット履歴を更新する
     */
    private void updateChatLabel() {
        chatLabel.setText("<html>" + chatTemplate.getChatHistoryForUser().replaceAll("\n", "<br>").replaceAll("<br>Assistant:", "<hr><br>Assistant:").replaceAll("<br>You:", "<hr><br>You:") + "</html>");
    }

    /**
     * 検索結果を抽出する
     * 
     * ユーザーの入力と構造化されたメモを受け取り、検索結果を抽出する
     * 
     * @param userInput ユーザーの入力
     * @param structuredMemo 構造化されたメモ
     * 
     * @return 検索結果
     */
    private String extractSearchResult(String userInput, String structuredMemo) {
        String searchPrompt = chatTemplate.getPromptForSearchMemo(structuredMemo, userInput);
        String result = getLLMResponse(searchPrompt);
        int startIndex = result.indexOf("<|start_output|>") + "<|start_output|>".length();
        int endIndex = result.indexOf("<|end_output|>");
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return result.substring(startIndex, endIndex).trim();
        }
        return "";
    }

    /**
     * メモを保存する
     */
    private void saveMemo() {
        String memoText = memoArea.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(memoParams.getFilePath()))) {
            writer.write(memoText);
            memoParams.setContents(memoText);
            JOptionPane.showMessageDialog(this, "Saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save.");
        }
    }
}

package 프로젝트;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;

public class WinNER extends JFrame implements ActionListener {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
//	FileAndReplace 
//	public static WinNER frmMain = new WinNER();
	String blank = "";
//	int findCnt = 0;
	Integer[] size = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72 };
	JCheckBox bold = new JCheckBox("bold"), italic = new JCheckBox("기울기"), custom = new JCheckBox("임의로 입력");
	JTextField sizeType = new JTextField("18", 20);
	JComboBox combo = new JComboBox(size);

	JFrame jf = new JFrame("WinNER");
	JButton jb = new JButton("찾기");
	JButton jb2 = new JButton("바꾸기");

	JTextField tf = new JTextField("찾을 단어", 15);
	JTextField tf2 = new JTextField("바꿀 단어", 15);
	JTextArea ta = new JTextArea(26, 69);
	JLabel jl = new JLabel();
	UndoManager editManager = new UndoManager();
	
//	URL logo = getClass().getResource("Logo.png");
//	URL icon = getClass().getResource("Icon.png");
	
//	File pathLogo = new File(logo.getPath());
	ImageIcon lo = new ImageIcon("C:/Users/emt00/Desktop/2조/Logo.png");
	ImageIcon ic = new ImageIcon("C:/Users/emt00/Desktop/2조/Icon.png");
	Image loImage = lo.getImage();
	Image image = loImage.getScaledInstance(400, 100, Image.SCALE_SMOOTH);

	JPanel jp = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp4 = new JPanel();

	JDialog jdInfo = new JDialog(jf, "앱 정보");
	JDialog jdFind = new JDialog(jf, "단어 찾기");
	JDialog jdReplace = new JDialog(jf, "바꾸기");
	JDialog jdFont = new JDialog(jf, "글꼴");

	Container cp = jf.getContentPane(); // 컨텐트팬 알아내기

	public class HandlerClass implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {

			Font font = null;
			Document doc = sizeType.getDocument();
			int text;
			try {
				if (!custom.isSelected()) {
					sizeType.setText(String.valueOf(combo.getSelectedItem()));
				}
				text = Integer.parseInt(doc.getText(0, doc.getLength()));

				font = new Font("Serif", Font.PLAIN, text);
				if (bold.isSelected() && italic.isSelected()) {
					font = new Font("Serif", Font.BOLD + Font.ITALIC, text);
				} else if (bold.isSelected()) {
					font = new Font("Serif", Font.BOLD, text);
				} else if (italic.isSelected()) {
					font = new Font("Serif", Font.ITALIC, text);
				} else
					font = new Font("Serif", Font.PLAIN, text);

				ta.setFont(font);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	public class HighLightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public HighLightPainter(Color color) {
			super(color);
		}
	}

	Highlighter.HighlightPainter hlp = new HighLightPainter(Color.YELLOW);

	public void highlighter(JTextComponent tc, String word) {
		try {
			Highlighter hl = tc.getHighlighter();
			Document doc = tc.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;
			if (!word.equals("")) {
				while ((pos = text.toUpperCase().indexOf(word.toUpperCase(), pos)) >= 0) {
					hl.addHighlight(pos, pos + word.length(), hlp);
					pos += word.length();
//					findCnt++;
//					System.out.println(findCnt);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();

		}
	}

	public void delHL(JTextComponent tc, String word) {
		try {
			Highlighter hl = tc.getHighlighter();
			hl.removeAllHighlights();
//			Highlighter.Highlight[] hilights = hl.getHighlights();
			/*
			 * for (int i = 0; i < hilights.length; i++) { if (hilights[i].getPainter()
			 * instanceof HighLightPainter) { hl.removeHighlight(hl); } }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO: handle exception
	}

	public void FindWord() {
		jp2.setLayout(new FlowLayout());
		jp2.add(tf);
		jp2.add(jb);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JTextComponent tc = new JTextComponent();
				String str7 = e.getActionCommand();
				if (str7.equals("찾기")) {
					highlighter(ta, tf.getText());
				}
			}
		});
		jdFind.add(jp2);
		jdFind.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

//				System.out.println("Closed");
				delHL(ta, tf.getText());
				e.getWindow().dispose();
			}
		});
		jdFind.setVisible(true);
		jdFind.setResizable(false);
		jdFind.setSize(300, 150);
	}

	public void ReplaceWord() {
		jp4.setLayout(new FlowLayout());
		jp4.add(tf);
		jp4.add(jb);
		jp4.add(tf2);
		jp4.add(jb2);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JTextComponent tc = new JTextComponent();
				String str7 = e.getActionCommand();
				if (str7.equals("찾기")) {
					highlighter(ta, tf.getText());
				}
			}
		});
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str9=e.getActionCommand();
				if (str9.equals("바꾸기")) {
					String findW = tf.getText(), text = ta.getText(), replaceW = tf2.getText();
					String str8 = ta.getText();
					int index = 0;
					int pos = str8.indexOf(findW, index);
					if (pos == -1) {
						pos = 0;
						index = 0;
						pos = str8.indexOf(findW, index);
						index = pos + findW.length();
					} else {
						index = pos + findW.length();
//					// ta.replaceSelection(tf2.getText());
//					ta.setText(ta.getText().toLowerCase().replace(tf.getText(), tf2.getText()));
//					ta.setText(ta.getText().toUpperCase().replaceAll(tf.getText().toUpperCase(), tf2.getText()));
//					ta.replaceRange(replaceW, pos, index);
//					ta.replaceRange(replaceW, pos, index);
						ta.setText(str8.toLowerCase().replaceFirst(tf.getText(), replaceW));
					}
				}
			}
		});
		jdReplace.add(jp4);
		jdReplace.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

//				System.out.println("Closed");
				delHL(ta, tf.getText());
				e.getWindow().dispose();
			}
		});
		jdReplace.setVisible(true);
		jdReplace.setResizable(false);
		jdReplace.setSize(300, 150);
	}

	public void ctrlZorY() {
		ta.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				editManager.addEdit(e.getEdit());
			}
		});
		ta.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				/* LocalDateTime startTime = getCurrentTime(); */
//				undoManager.undo();
			}

			@Override

			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					editManager.undo();
				} else if ((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					editManager.redo();
				}
			}
		});

	}

	WinNER() {
		jf.setIconImage(ic.getImage());
		jf.setTitle("WinNER");
		ta.setLineWrap(true);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE); // x버튼 클릭 시 프로그램 종료
		cp.setLayout(new FlowLayout());
//		cp.setBackground(Color.WHITE); // 흰색으로 배경 설정
		JScrollPane sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cp.add(sp);

		createMenu();
		jf.setSize(800, 490); // 프레임 크기 800x540 설정
		jf.setResizable(false);
		jf.setVisible(true); // 화면에 프레임 출력
	}

	public void createMenu() {
		JMenuBar menu = new JMenuBar();

		JMenu io = new JMenu("파일");
		JMenu ed = new JMenu("편집");
		JMenu st = new JMenu("스타일");
		JMenu to = new JMenu("도구");
		JMenu he = new JMenu("도움말");

		JMenuItem newFile = new JMenuItem("새 파일");
		JMenuItem openFile = new JMenuItem("파일 열기");
		JMenuItem saveAsFile = new JMenuItem("다른 이름으로 파일 저장");
		JMenuItem Exit = new JMenuItem("종료하기");

		JMenuItem ctrlz = new JMenuItem("뒤로 가기");
		JMenuItem ctrly = new JMenuItem("앞으로 가기");
		JMenuItem ctrlf = new JMenuItem("단어 찾기");
		JMenuItem replaceword = new JMenuItem("단어 바꾸기");
		JMenuItem f5 = new JMenuItem("시간 출력");

		JMenuItem font = new JMenuItem("글꼴");
		JMenuItem command = new JMenuItem("커맨드");
		JMenuItem help = new JMenuItem("github link");
		JMenuItem info = new JMenuItem("앱 정보");

		io.add(newFile);
		io.add(openFile);
		io.add(saveAsFile);
		io.addSeparator();
		io.add(Exit);

		ed.add(ctrlz);
		ed.add(ctrly);
		ed.addSeparator();
		ed.add(ctrlf);
		ed.add(replaceword);
		ed.addSeparator();
		ed.add(f5);

		st.add(font);
		to.add(command);

		he.add(help);
		he.addSeparator();
		he.add(info);

		ctrlf.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_F, (char) KeyEvent.VK_ALT));
		ctrlz.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_Z, (char) KeyEvent.VK_ALT));
		ctrly.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_Y, (char) KeyEvent.VK_ALT));
		replaceword.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_R, (char) KeyEvent.VK_ALT));
		replaceword.addActionListener(new ActionListener() {
			public void actionPerformed(KeyEvent e) {
//				System.out.println("replace");
				ReplaceWord();
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("replace");
			}
		});
		f5.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_F5, 0));
		f5.addActionListener(new ActionListener() {
			public void actionPerformed(KeyEvent e) {
				Date today = new Date();

				SimpleDateFormat dateANDtime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");

				ta.append("Date: " + dateANDtime.format(today));
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		newFile.addActionListener(this);
		openFile.addActionListener(this);
		saveAsFile.addActionListener(this);
		Exit.addActionListener(this);

		ctrlz.addActionListener(this);
		ctrly.addActionListener(this);
		ctrlf.addActionListener(this);
		replaceword.addActionListener(this);
		f5.addActionListener(this);

		font.addActionListener(this);
		command.addActionListener(this);
		help.addActionListener(this);
		info.addActionListener(this);

		menu.add(io);
		menu.add(ed);
		menu.add(st);
		menu.add(to);
		menu.add(he);

		jf.setJMenuBar(menu);
	}

	/*
	 * public void actionPerformed(KeyEvent e) { switch(e.getKeyCode()) { case
	 * KeyEvent.VK_F: if(e.isControlDown()) { FindWord(); } } }
	 */

	public void actionPerformed(ActionEvent e) {
		FileDialog saveF = new FileDialog(this, "다른 이름으로 파일 저장", FileDialog.SAVE);
		FileDialog openF = new FileDialog(this, "파일 열기", FileDialog.LOAD);

		String str = e.getActionCommand();
//		System.out.println(str);
		if (str.equals("새 파일")) {
			ta.setText(blank);
			jf.setTitle("WinNER");
			jf.setSize(800, 490);
		} else if (str.equals("파일 열기")) {
			openF.setVisible(true);
			String data = openF.getDirectory() + openF.getFile();
			try {
				String str1 = "";
				char str2;
				File file = new File(data);
				FileInputStream fileIO = new FileInputStream(file);
//				InputStreamReader inputRead = new InputStreamReader(fileIO,"UTF-8");
				BufferedReader br = new BufferedReader(new FileReader(data));
//				BufferedReader br = new BufferedReader(inputRead);

				/*
				 * while ((str1 = br.readLine()) != null) { str1 = str1 + "\r\n" + str2; //
				 * ta.append(str); // ta.append("\r\n"); }
				 */
				/*
				 * double fileSize = (double) file.length(); byte byteArr[] = new byte[(int)
				 * fileSize]; while(fileIO.read(byteArr)!=-1) { for (int i = 0; i <
				 * fileIO.read(byteArr); i++) { str1+=(char)byteArr[i]; } }
				 */

				for (int i = 0; i < (int) file.length(); i++) {
					str2 = (char) fileIO.read();
					str1 += str2;
				}
				ta.setText(str1);
				fileIO.close();
				br.close();
				String name = openF.getFile();
				jf.setTitle(name);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (str.equals("다른 이름으로 파일 저장")) {
			saveF.setVisible(true);
			String data = saveF.getDirectory() + saveF.getFile();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(data));
				String gt = ta.getText();
				for (int i = 0; i < gt.length(); i++) {
					if (gt.charAt(i) == '\n') {
						bw.newLine();
					} else {
						bw.write(gt.charAt(i));
					}
				}
				bw.close();
				String name = saveF.getFile();
				jf.setTitle(name);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} else if (str.equals("종료하기")) {
			System.exit(0);
		} else if (str.equals("뒤로 가기") || str.equals("앞으로 가기")) {
			ctrlZorY();
		} else if (str.equals("단어 찾기")) {
			FindWord();
//			new FindandRePlace(frmMain, false);
		} else if (str.equals("단어 바꾸기")) {
//			System.out.println("replace");
			ReplaceWord();
		} else if (str.equals("시간 출력")) {
			Date today = new Date();
			SimpleDateFormat dateANDtime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
			ta.append("Date: " + dateANDtime.format(today));
		} else if (str.equals("글꼴")) {
//			FontDialogClass fontDial = new FontDialogClass();

//			text22 = new JTextField("this is a sentece", 20);
//			ta.setFont(new Font("Serif", Font.PLAIN, 14));
			sizeType.setFont(new Font("Serif", Font.PLAIN, 18));

			jp3.setLayout(new FlowLayout());

			jp3.add(bold);
			jp3.add(italic);
			jp3.add(sizeType);
			jp3.add(combo);
			jp3.add(custom);

			jdFont.add(jp3);

			HandlerClass handler = new HandlerClass();
			custom.addItemListener(handler);
			bold.addItemListener(handler);
			italic.addItemListener(handler);
			combo.addItemListener(handler);
			jdFont.setSize(400, 300);
			jdFont.setBackground(Color.WHITE);
			jdFont.setVisible(true);
			jdFont.setResizable(false);

		} else if (str.equals("커맨드")) {
			Process ps = null;
//			String str4 = "";
			try {
				ps = new ProcessBuilder("cmd.exe", "/C", "start").start();

				/*
				 * BufferedReader stdOut = new BufferedReader(new
				 * InputStreamReader(ps.getInputStream()));
				 * 
				 * while ((str4 = stdOut.readLine()) != null) { System.out.println(str4); }
				 */

			} catch (Exception e1) { // TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		else if (str.equals("github link")) {
			URI uri;
			try {
				uri = new URI("https://github.com/yuto3125");
				Desktop.getDesktop().browse(uri);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (str.equals("앱 정보")) {
			jp.setLayout(new FlowLayout(FlowLayout.LEFT));
//			jp.setBackground(Color.WHITE);
			ImageIcon icon = new ImageIcon(image);
			JSeparator js = new JSeparator();
//			js.setOrientation(SwingConstants.HORIZONTAL);

			jl = new JLabel(icon);
			jp.add(jl);
			jp.add(js);
			jl = new JLabel(
					"----------------------------------------------------------------------------------------------------------------------");
			jp.add(jl);
			jl = new JLabel("⚠　저작권 등록을 하지 않았긴 했지만 함부로 다른 곳에 올리지 맙시다.　　　　　　　　　　　　");
			jp.add(jl);
			jl = new JLabel("2조의 프로젝트 made by 박준석, 염재준, 안상빈, 박종호　　　　　　　　　　　　　　　　　");
			jp.add(jl);
			jl = new JLabel("버전 0.8.0");// 100만점에 80점
			jp.add(jl);
			jdInfo.add(jp);

			jdInfo.setVisible(true);
			jdInfo.setSize(500, 300);
		}
	}

	public static void main(String[] args) {
		new WinNER();
	}

}

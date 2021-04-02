package patchDownloader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HomeFrame {

	private JFrame frame;
	String folderLocation, foldername, sysreportname, sysreportLocation, line;
	List<Integer> modulesList = new ArrayList<>();
	boolean ok;
	private JLabel lblNewLabel_4;
	private JButton submit_button;
	JList<String> list;
	JLabel lblNewLabel_3;
	JLabel lblNewLabel_3_1;
	public static Logger logger;
	Map<String, String> map = new HashMap<>();
	JList list_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeFrame window = new HomeFrame();
					window.frame.setVisible(true);
					logger = Logger.getLogger("patchDownloader");

					// Simple file logging Handler.
					FileHandler fileHandler;
					File dir = new File("C:\\PatchDownloader\\logs");
					if (!dir.exists()) {
						if (dir.mkdirs()) {
							System.out.println("Logs directory is created!");
						} else {
							System.out.println("Logs directory is already avilable");
						}
					}

					fileHandler = new FileHandler("/PatchDownloader/logs/patchlist.log", true);
					logger.addHandler(fileHandler);

					SimpleFormatter formatter = new SimpleFormatter();
					fileHandler.setFormatter(formatter);
					
					logger.info("Log file values.");
					fileHandler.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public HomeFrame() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(17, 27, 88));
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("PatchDownloader");
		Image icon = new ImageIcon(this.getClass().getResource("/icon.png")).getImage();
		frame.setIconImage(icon);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setBounds(x - 400, y - 350, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Patch Downloader");
		lblNewLabel.setBounds(168, 75, 586, 37);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		frame.getContentPane().add(lblNewLabel);

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setForeground(SystemColor.text);
		lblNewLabel_3.setBounds(639, 190, 141, 17);
		frame.getContentPane().add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Select File");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBounds(514, 186, 115, 21);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt", "text");
				fileChooser.setFileFilter(filter);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					lblNewLabel_3.setText(selectedFile.getName());
					sysreportname = selectedFile.getName();
					sysreportLocation = selectedFile.getAbsolutePath();
				}
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Upload System Report  :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(246, 170, 179, 48);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2_1 = new JLabel("OTHOME Location   :");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(235, 222, 190, 48);
		frame.getContentPane().add(lblNewLabel_2_1);

		lblNewLabel_3_1 = new JLabel("");
		lblNewLabel_3_1.setForeground(SystemColor.text);
		lblNewLabel_3_1.setBounds(639, 242, 141, 17);
		frame.getContentPane().add(lblNewLabel_3_1);

		JButton btnNewButton_1 = new JButton("Select Folder");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setBounds(514, 238, 115, 21);
		frame.getContentPane().add(btnNewButton_1);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					lblNewLabel_3_1.setText(selectedFile.getName());
					foldername = selectedFile.getName();
					folderLocation = selectedFile.getAbsolutePath();
					System.out.print(folderLocation);
					logger.info("Selected OT HOME: " + folderLocation);
				}
			}
		});

		submit_button = new JButton("");
		submit_button.setText("Submit");
		submit_button.setBounds(411, 449, 85, 21);
		submit_button.addActionListener(new MyAction());
		frame.getContentPane().add(submit_button);

		JList list = new JList();

		list.setBackground(Color.WHITE);
		list.setVisibleRowCount(4);

		DefaultListModel<Object> model_list = new DefaultListModel<Object>();
		BufferedReader bufReader = null;
		try {
			InputStream is = getClass().getResourceAsStream("modules.txt");
			InputStreamReader isr = new InputStreamReader(is);
			bufReader = new BufferedReader(isr);
			line = bufReader.readLine();
			while (line != null) {
				String[] res = line.split("[,]", 0);
				model_list.addElement(res[0]);
				map.put(res[0], res[1]);
				line = bufReader.readLine();
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		list.setModel(model_list);
		list.setBounds(310, 242, 203, 80);
		frame.getContentPane().add(list);

		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(70, 480, 758, 84);

		frame.getContentPane().add(lblNewLabel_4);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(93, 300, 260, 125);
		frame.getContentPane().add(scrollPane);

		JButton leftToRightButton = new JButton(">>>");
		leftToRightButton.setBounds(425, 317, 60, 21);
		frame.getContentPane().add(leftToRightButton);

		JButton rightToLeftButton = new JButton("<<<");
		rightToLeftButton.setBounds(425, 363, 60, 21);
		frame.getContentPane().add(rightToLeftButton);
		DefaultListModel<Object> model_list_1 = new DefaultListModel<Object>();

		list_1 = new JList();
		list_1.setBounds(712, 242, 179, 68);
		frame.getContentPane().add(list_1);
		list_1.setModel(model_list_1);

		JScrollPane scrollPane_1 = new JScrollPane(list_1);
		scrollPane_1.setBounds(568, 300, 260, 125);
		frame.getContentPane().add(scrollPane_1);

		leftToRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				list.getSelectedValuesList().stream().forEach((data) -> {
					model_list_1.addElement(data);
					model_list.removeElement(data);
					List list = Collections.list(model_list_1.elements());
					Collections.sort(list); // sort
					model_list_1.clear(); // remove all elements
					for (Object o : list) {
						model_list_1.addElement(o);
					}
				});
			}
		});

		rightToLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				list_1.getSelectedValuesList().stream().forEach((data) -> {
					model_list_1.removeElement(data);
					model_list.addElement(data);
					List list = Collections.list(model_list.elements());
					Collections.sort(list); // sort
					model_list.clear(); // remove all elements
					for (Object o : list) {
						model_list.addElement(o);
					}
				});
			}
		});

	}

	public class MyAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					String msg = validate();
					String warning = null;
					if (msg.length() == 0) {
						warning = checkModule();
					}
					modulesList.clear();
					if (msg.length() == 0 && warning.length() == 0) {
						lblNewLabel_4.setForeground(Color.WHITE);
						lblNewLabel_4.setText("Patch downloading and extraction is in progress...");
						submit_button.setEnabled(false);

						List<Integer> tempList = new ArrayList<>();

						logger.info("Selected Sysreport path: " + sysreportLocation);

						logger.info("Selected Modules list: ");
						// Get all the selected items using the indices
						for (int i = 0; i < list_1.getModel().getSize(); i++) {
							Object sel = list_1.getModel().getElementAt(i);

							String moduleName = "	....Comment: Module:    " + sel;
							String module = "" + sel;
							System.out.println("SystemreportLocation->" + sysreportLocation);
							System.out.println(moduleName);
							logger.info(module);
							try {
								tempList = parseSystemReport(sysreportLocation, moduleName);
								modulesList.addAll(tempList);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						parseAndDownload parser = new parseAndDownload();

						try {
							ok = parser.parse(sysreportLocation, folderLocation, sysreportname, modulesList);

						} catch (Exception ex) {
							// some errors occurred
							ex.printStackTrace();
						}

						submit_button.setEnabled(true);
						submit_button.setText("Submit");

						if (ok) {
							lblNewLabel_4.setForeground(Color.GREEN);
							lblNewLabel_4
									.setText("Patches downloaded and extracted to " + foldername + " successfully!!");
							logger.info("Patches downloaded and extracted to " + foldername + " successfully!!");
						} else {
							lblNewLabel_4.setForeground(Color.YELLOW);
							lblNewLabel_4.setText("Patch downloading and extraction completed with errors.");
							logger.info("Patch downloading and extraction completed with errors");
						}

					} else {
						if (msg.length() != 0) {
							lblNewLabel_4.setForeground(Color.RED);
							lblNewLabel_4.setText(msg);
						} else {
							lblNewLabel_4.setForeground(Color.RED);
							lblNewLabel_4.setText(warning);
						}
					}
				}
			});

			t.start();

		}

		private List<Integer> parseSystemReport(String sysreportLocation, String moduleName) throws Exception {
			// TODO Auto-generated method stub
			System.out.println(sysreportLocation);
			System.out.println(moduleName);
			List<Integer> patchlist = new ArrayList<>();

			try {
				BufferedReader buf = new BufferedReader(
						new InputStreamReader(new DataInputStream(new FileInputStream(sysreportLocation))));
				String line;

				int lineNumber = 0, j = 0;
				System.out.println(buf);
				while ((line = buf.readLine()) != null) {
					lineNumber++;
					// System.out.println(line);
					if (moduleName.equals(line)) {
						System.out.println(lineNumber);
						patchlist.add(lineNumber);
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return patchlist;
		}

		private String validate() {
			if (lblNewLabel_3.getText().trim().length() == 0) {
				return "Please select the system report";
			}
			if (lblNewLabel_3_1.getText().trim().length() == 0) {
				return "Please select the OTHOME Location";
			}
			if (list_1.getModel().getSize() == 0) {
				return "Please select at least one module";
			}

			return "";
		}

		private String checkModule() {

			String unAvailableModules = "";
			int count = 0;
			File dir = new File(folderLocation + "\\module");
			File filesList[] = dir.listFiles();
			ArrayList<String> results = new ArrayList<String>();

			for (File file : filesList) {
				if (file.isDirectory()) {
					results.add(file.getName());
				}
			}

			for (int i = 0; i < list_1.getModel().getSize(); i++) {
				Object sel = list_1.getModel().getElementAt(i);

				String module = "" + sel;
				logger.info(module);
				Object mFolder = map.get(sel);
				if (mFolder != "") {
					System.out.println(module + " mfolder" + mFolder);
					boolean test = results.contains(mFolder);
					if (!test) {
						unAvailableModules += module + ", ";
						count++;
					}
				}

			}

			if (count != 0) {

				unAvailableModules = unAvailableModules.substring(0, unAvailableModules.length() - 2);
				String unAvailableModulesList;

				if (count == 1) {
					unAvailableModulesList = " Following Module is not installed in the selected instance: "
							+ unAvailableModules;
					unAvailableModules = " Following Module is not installed in the selected instance: "
							+ unAvailableModules;

				} else {
					unAvailableModulesList = " Following Modules are not installed in the selected instance: "
							+ unAvailableModules;
					unAvailableModules = " Some of the selected modules are not installed in the CS instance. Please refer to log file";

				}
				logger.info(unAvailableModulesList);
				return unAvailableModules;

			}
			return "";
		}
	}
}

package gui.screens;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.tree.TreeSelectionModel;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;

import gui.types.MyTIOSTS;
import gui.util.IconNode;
import gui.util.SRT;
import gui.util.SimpleNode;
import gui.util.TreeRenderer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTree tree;
	private MyTIOSTS selected;
	private JButton[] buttonArray;
	private JTextPane textEditor = new JTextPane();
	private HashMap<DefaultMutableTreeNode, MyTIOSTS> treeTIOSTS = new HashMap<DefaultMutableTreeNode, MyTIOSTS>();
	private String name;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// splitPane
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		// tabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
			
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("TIOSTS", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		// tree
		name = "TIOSTS";
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new IconNode(name, "/gui/icons/tab.png"));
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new SimpleNode("System Declarations"));

		root.add(node);
		
        tree = new JTree(root);
        tree.setCellRenderer(new TreeRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		MyTIOSTS firstTIOSTS = new MyTIOSTS("TIOSTS");
		setSelected(firstTIOSTS);
		
		insertNode(null, firstTIOSTS);
		scrollPane.setViewportView(tree);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				int cont = tree.getSelectionModel().getLeadSelectionRow();
				tabbedPane.setSelectedIndex(0);
				panel_1.removeAll();
				panel_1.repaint();
				panel_1.revalidate();
				statusButton(false);
				if(cont == 0) {
					renderGraph(panel_1, null);	
				}else if(cont == 1) {
					panel_1.add(textEditor, BorderLayout.CENTER);
					panel_1.repaint();
					panel_1.revalidate();
				}else if(cont>1){
					DefaultMutableTreeNode ab = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					DefaultMutableTreeNode parentAab = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getParent();
					
					if(treeTIOSTS.containsKey(ab)) {
						selected = treeTIOSTS.get(ab);
						renderGraph(panel_1, selected);
					}
					else if(treeTIOSTS.containsKey(parentAab)){
						panel_1.add(treeTIOSTS.get(parentAab).getText(), BorderLayout.CENTER);
						panel_1.repaint();
						panel_1.revalidate();
					}
				}
				addMouseEvent(selected);
			}
		});
		
		// menuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
				
		JMenu menu1 = new JMenu("File");
		menuBar.add(menu1);
			
		JMenu menu2 = new JMenu("Preview");
		menuBar.add(menu2);
		
		JMenuItem itemMenu1 = new JMenuItem("Add TIOSTS");
		itemMenu1.setIcon(new ImageIcon(Main.class.getResource("/gui/icons/graph.png")));
		itemMenu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertNode(null, null);
			}
		});
		menu1.add(itemMenu1);
		
		JMenuItem itemMenu2 = new JMenuItem("Hidden/Show");
		itemMenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				selected.getGraph().getView().
			}
		});
		menu2.add(itemMenu2);
		// buttons
		buttonArray = new JButton[5];
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
				
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar);
				
		buttonArray[0] = new JButton("");
		buttonArray[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				selectButton(button1);
			}
		});
		toolBar.add(buttonArray[0]);
		buttonArray[0].setIcon(new ImageIcon(Main.class.getResource("/gui/icons/handCursor.png")));
		
		buttonArray[1] = new JButton("");
		buttonArray[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertVertex();
			}
		});
		toolBar.add(buttonArray[1]);
		buttonArray[1].setIcon(new ImageIcon(Main.class.getResource("/gui/icons/Add-icon.png")));
			
		buttonArray[2] = new JButton("");
		buttonArray[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EdgeInsert a = new EdgeInsert(selected);
				a.setVisible(true);
			}
		});
		toolBar.add(buttonArray[2]);
		buttonArray[2].setIcon(new ImageIcon(Main.class.getResource("/gui/icons/right.png")));
		
		buttonArray[3] = new JButton("");
		buttonArray[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SRT createSRT = new SRT(treeTIOSTS.values(), textEditor.getText());
				createSRT.run();
			}
		});
		toolBar.add(buttonArray[3]);
		buttonArray[3].setIcon(new ImageIcon(Main.class.getResource("/gui/icons/printer.png")));
		
		buttonArray[4] = new JButton("");
		buttonArray[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mxHierarchicalLayout layout = new mxHierarchicalLayout(selected.getGraph());
			    layout.setOrientation(SwingConstants.WEST);
			    layout.execute(selected.getGraph().getDefaultParent());
			}
		});
		toolBar.add(buttonArray[4]);
		buttonArray[4].setIcon(new ImageIcon(Main.class.getResource("/gui/icons/organize.png")));
		statusButton(false);
		addKeysEvent();
	}
	
	public MyTIOSTS getSelected() {
		return selected;
	}

	public void setSelected(MyTIOSTS selected) {
		this.selected = selected;
	}
	
	public JEditorPane getProcessText() {
		return textEditor;
	}

	public void setProcessText(JTextPane textEditor) {
		this.textEditor = textEditor;
	}
	
	private void addMouseEvent(MyTIOSTS tiosts) {
		tiosts.getGraphComponent().getGraphControl().addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(e.getClickCount()==2 && !e.isConsumed()){
					e.consume();
					Object cell = tiosts.getGraphComponent().getCellAt(e.getX(), e.getY());
					if (cell != null){
						if(((mxCell) cell).isVertex()) {
							VertexEdit newFrame = new VertexEdit(tiosts, (mxCell) cell);
							newFrame.setVisible(true);							
						}else {
							EdgeEdit newFrame = new EdgeEdit(tiosts, (mxCell) cell);
							newFrame.setVisible(true);							
						}
					}
		        }
			}
		});
	}
	
	private void addKeysEvent() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		        if(e.getKeyCode()==127 && !e.isConsumed()) {
		        	e.consume();
		        	Object[] selectionCells = selected.getGraph().getSelectionCells();
		        	selected.getGraph().removeCells();
		        	for(Object selectionCell : selectionCells) {
		        		selected.getAllCells().remove((mxCell) selectionCell);
		        	}
		        }
		        return false;
		      }
		});
	}
	
	public void insertNode(String name, MyTIOSTS newTIOSTS) {
		if(name == null) {
			name = "new TIOSTS";
		}
		IconNode node1 = new IconNode(name, "/gui/icons/graph.png");
		SimpleNode node2 = new SimpleNode("Local Declarations");
		
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		
		DefaultMutableTreeNode schema = new DefaultMutableTreeNode(node1);
		root.add(schema);
        
		DefaultMutableTreeNode sheet = new DefaultMutableTreeNode(node2);
		schema.add(sheet);
		
		model.reload(root);		
		if(newTIOSTS==null) {
			MyTIOSTS novo = new MyTIOSTS("new TIOSTS");
			treeTIOSTS.put(schema, novo);
		}else {
			treeTIOSTS.put(schema, newTIOSTS);
		}
	}
	
	public void statusButton(Boolean value) {
		for(JButton button : buttonArray) {
			button.setEnabled(value);
		}
	}

	public void insertVertex() {
		selected.getGraph().getModel().beginUpdate();
		try{
			selected.getGraphComponent().getGraphControl().addMouseListener((MouseListener) new MouseAdapter() {
		    	public void mouseReleased(MouseEvent e){
		    		VertexInsert inserir = new VertexInsert(selected, e.getX(), e.getY());
		    		inserir.setVisible(true);
		    		selected.getGraphComponent().getGraphControl().removeMouseListener((MouseListener) this);
				}
		    });
		}finally{
			selected.getGraph().getModel().endUpdate();
		}
	}
	
	public void renderGraph(JPanel panel, MyTIOSTS novoTIOSTS) {
		JPanel panel_123 = new JPanel();
		panel_123.setLayout(new BorderLayout(0, 0));
		
		JTextField textField = new JTextField();
		panel_123.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome: ");
		panel_123.add(lblNewLabel, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("Salvar");
		panel_123.add(btnNewButton, BorderLayout.EAST);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().trim().equals("")) {
					String newName = textField.getText().replace(" ", "").replace("	", "");
					if(novoTIOSTS!=null) {
						novoTIOSTS.setName(newName);
					}
					((IconNode)((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).getUserObject()).setName(newName);
				}else {
					JOptionPane.showMessageDialog(null, "Invalid name", "Warning" , JOptionPane.WARNING_MESSAGE);
				}
				tree.repaint();
				tree.revalidate();
				textField.setText("");
			}
		});
		
		panel.add(panel_123, BorderLayout.NORTH);
		if(novoTIOSTS==null) {
			panel.add(new JPanel(), BorderLayout.CENTER);			
		}else {
			panel.add(novoTIOSTS.getGraphComponent(), BorderLayout.CENTER);
			statusButton(true);
		}
		panel.repaint();
		panel.revalidate();
	}
}
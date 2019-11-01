package mai.linh.gui.swing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import mai.linh.gui.swing.util.Utilities;

/**
 * A list uses an object called a cell renderer to display each of its items. 
 * The default cell renderer knows how to display strings and icons and it 
 * displays Objects by invoking toString. If you want to change the way the 
 * default renderer display icons or strings, or if you want behavior different 
 * than what is provided by toString, you can implement a custom cell renderer. 
 * Take these steps to provide a custom cell renderer for a list:
 * - Write a class that implements the ListCellRenderer interface.
 * - Create an instance of your class and call the list's setCellRenderer using 
 *   the instance as an argument.
 */
public class CustomList extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String RESOURCE_PATH = "/resources/flags";

	private JList<CountryItem> list;

	private DefaultListModel<CountryItem> listModel;

	private ListCellRenderer<CountryItem> listRenderer;

	/**
	 * MAIN
	 * @param args
	 */
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomList frame = new CustomList();
					Utilities.setFrameLocationCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	/**
	 * 
	 */
	public CustomList() {
		super("Custom list model and renderer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 248, 400);

		list = new JList<CountryItem>();
		listModel = new DefaultListModel<CountryItem>();
		listRenderer = new CountryListRenderer();

		list.setModel(listModel);
		list.setCellRenderer(listRenderer);
		list.setSelectedIndex(1);

		listModel.addElement(new CountryItem("Belgium"    , "What a country", RESOURCE_PATH  + "/belgium.gif"));
		listModel.addElement(new CountryItem("Brazil"     , "What a country", RESOURCE_PATH  + "/brazil.gif"));
		listModel.addElement(new CountryItem("Colombia"   , "What a country", RESOURCE_PATH  + "/colombia.gif"));
		listModel.addElement(new CountryItem("Indonesia"  , "What a country", RESOURCE_PATH  + "/indonesia.gif"));
		listModel.addElement(new CountryItem("Jamaica"    , "What a country", RESOURCE_PATH  + "/jamaica.gif"));
		listModel.addElement(new CountryItem("Mozambique" , "What a country", RESOURCE_PATH  + "/mozambique.gif"));
		listModel.addElement(new CountryItem("Philippines", "What a country", RESOURCE_PATH  + "/philippines.gif"));
		listModel.addElement(new CountryItem("Saotome"    , "What a country", RESOURCE_PATH  + "/saotome.gif"));
		listModel.addElement(new CountryItem("Spain"      , "What a country", RESOURCE_PATH  + "/spain.gif"));
		listModel.addElement(new CountryItem("Suriname"   , "What a country", RESOURCE_PATH  + "/suriname.gif"));
		listModel.addElement(new CountryItem("USA"        , "What a country", RESOURCE_PATH  + "/usa.gif"));

		JScrollPane listScroller = new JScrollPane(list);
		getContentPane().add(listScroller);
	}

	/**
	 * Editable label with CardLayout
	 * @author mail
	 *
	 */
	class EditableLabel extends JPanel {
		private static final long serialVersionUID = 1L;
		private CardLayout layout = new CardLayout();

		public EditableLabel() 
		{
			super();
			setLayout(layout);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					if (event.getClickCount() == 2) {
					}
				}
			});
		}
	}

	/**
	 * custom cell renderer that represents a country by JLabel
	 */
	class CountryListRenderer extends JLabel implements ListCellRenderer<CountryItem> {
		private static final long serialVersionUID = 1L;

		CountryListRenderer()
		{
			super();
			setOpaque(true);
			setHorizontalAlignment(LEADING);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends CountryItem> list, 
				CountryItem item,
				int index, 
				boolean isSelected, 
				boolean cellHasFocus) 
		{

			setIcon(item.getFlag());
			setText(item.getName());

			if (isSelected) {
				setBackground(Color.BLUE);
			}
			else {
				setBackground(Color.WHITE);
			}

			return this;
		}

	}

	/**
	 * Custom list item
	 * @author mail
	 */
	class CountryItem {
		private String name;
		private String info;
		private ImageIcon flag;

		public CountryItem(String name, String info, String pathToIcon) {
			this.name = name;
			this.info = info;
			this.flag = new ImageIcon(Animation.class.getResource(pathToIcon));
			if (flag == null) {
				System.out.println("Cannot load icon at " + pathToIcon);
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public ImageIcon getFlag() {
			return flag;
		}

		public void setFlag(ImageIcon flag) {
			this.flag = flag;
		}
	}
}

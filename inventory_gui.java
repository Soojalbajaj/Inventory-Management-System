import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class product {
    String name;
    String id;
    int qty;

    product(String name, String id, int qty) {
        this.name = name;
        this.id = id;
        this.qty = qty;
    }

    public String toString() {
        return name + " | " + id + " | Qty: " + qty;
    }
}

class inventory_manager {
    ArrayList<product> list = new ArrayList<>();

    boolean add(product p) {
        if (find(p.id) != null)
            return false;
        list.add(p);
        return true;
    }

    product find(String id) {
        for (product p : list) {
            if (p.id.equals(id))
                return p;
        }
        return null;
    }

    boolean update(String id, int q) {
        product p = find(id);
        if (p == null)
            return false;
        p.qty = q;
        return true;
    }

    boolean del(String id) {
        return list.removeIf(p -> p.id.equals(id));
    }

    ArrayList<product> all() {
        return list;
    }
}

public class inventory_gui extends JFrame implements ActionListener {
    JTextField name_f, id_f, qty_f, search_f;
    JTextArea area;
    JButton add_btn, view_btn, search_btn, delete_btn, update_btn;
    inventory_manager m = new inventory_manager();

    inventory_gui() {
        setTitle("Inventory System");
        setSize(440, 440);
        setLayout(new FlowLayout());

        add(new JLabel("Name"));
        name_f = new JTextField(12);
        add(name_f);

        add(new JLabel("ID"));
        id_f = new JTextField(12);
        add(id_f);

        add(new JLabel("Qty"));
        qty_f = new JTextField(5);
        add(qty_f);

        add_btn = new JButton("Add");
        view_btn = new JButton("View");
        search_f = new JTextField(10);
        search_btn = new JButton("Search");
        update_btn = new JButton("Update");
        delete_btn = new JButton("Delete");

        add(add_btn);
        add(view_btn);
        add(search_f);
        add(search_btn);
        add(update_btn);
        add(delete_btn);

        area = new JTextArea(14, 33);
        area.setEditable(false);
        add(new JScrollPane(area));

        add_btn.addActionListener(this);
        view_btn.addActionListener(this);
        search_btn.addActionListener(this);
        delete_btn.addActionListener(this);
        update_btn.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add_btn) {
            try {
                String n = name_f.getText().trim();
                String i = id_f.getText().trim();
                int q = Integer.parseInt(qty_f.getText().trim());
                if (n.isEmpty() || i.isEmpty()) {
                    area.setText("Fill all fields");
                    return;
                }
                if (m.add(new product(n, i, q)))
                    area.setText("Added");
                else
                    area.setText("ID exists");
            } catch (Exception x) {
                area.setText("Invalid qty");
            }
        }

        if (e.getSource() == view_btn) {
            area.setText("");
            for (product p : m.all()) {
                area.append(p + "\n");
                if (p.qty < 5)
                    area.append("Low stock\n");
            }
        }

        if (e.getSource() == search_btn) {
            product p = m.find(search_f.getText().trim());
            if (p != null)
                area.setText("Found: " + p);
            else
                area.setText("Not Found");
        }

        if (e.getSource() == delete_btn) {
            if (m.del(search_f.getText().trim()))
                area.setText("Deleted");
            else
                area.setText("Not Found");
        }

        if (e.getSource() == update_btn) {
            try {
                int q = Integer.parseInt(qty_f.getText().trim());
                if (m.update(search_f.getText().trim(), q))
                    area.setText("Updated");
                else
                    area.setText("Not Found");
            } catch (Exception x) {
                area.setText("Invalid qty");
            }
        }
    }

    public static void main(String[] args) {
        new inventory_gui();
    }
}
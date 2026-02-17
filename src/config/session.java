package config;

public class session {
    private static session instance;
    private int account_id; 
    private int guestId; 
    private String fname, lname, username, email, pass, contact, status, role, type;

    private session() {}

    public static session getInstance() {
        if (instance == null) {
            instance = new session();
        }
        return instance;
    }

    // Single Universal ID Getter and Setter
    public int getAccountId() { return account_id; }
    public void setAccountId(int account_id) { this.account_id = account_id; }
    
    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
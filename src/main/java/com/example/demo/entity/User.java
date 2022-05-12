package com.example.demo.entity;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new LinkedList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_messages", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "message_id"))
    private List<ChatMessage> messages = new LinkedList<>();

    public User() {
    }

    public User(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String userName, String password, String firstName, String lastName, String email, List<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public User(String userName, String password, String firstName, String lastName, String email, List<Role> roles, List<ChatMessage> messages) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.messages = messages;
    }


    public void addRole(Role role) {
        try {
            this.roles.add(role);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void addMessage(ChatMessage chatMessage) {
        try {
            this.messages.add(chatMessage);
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getLastMessage() {
        try {
            int lastIdx = messages.size() - 1;
            ChatMessage lastElement = messages.get(lastIdx);
            return lastElement.toString();
        } catch (Exception e) {
            return "No messages yet";
        }
    }

    public String getFirstMessage() {
        try {
            ChatMessage firstElement = messages.get(0);
            return firstElement.toString();
        } catch (Exception e) {
            return "No messages yet";
        }
    }

    public int getNormalMessageLength() {
        int aetmltg = 0;
        for (ChatMessage mssg : this.messages) {
            aetmltg += mssg.getContent().length();
        }
        return (int) aetmltg / this.messages.size();
    }

    public String getReport() {
        String report = "" + this.toString() + "\n\n";
        report += "__________________________________________________________________________\n";
        if (messages.size() >= 1) {
            report += "First message: \n" + getFirstMessage() + "\n\n";
            report += "Last message: \n" + getLastMessage() + "\n\n";
            report += "De medium message length: \n" + getNormalMessageLength() + "\n\n";
            report += "Count of messages sent: \n" + messages.size() + "\n\n";

            report += "__________________________________________________________________________\n";
            for (ChatMessage mssg : messages) {
                report += mssg.toString();
            }
            report += "__________________________________________________________________________\n";
        } else {
            report += "Messages doest exist";
            report += "__________________________________________________________________________\n";
        }
        return report;
    }

    public File createFile() {
        try {
            File myObj = new File("src/main/resources/static/files", this.userName + ".txt");
            if (myObj.createNewFile()) {
                return myObj;
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    public File writeFile(File file) throws IOException {
        Files.writeString(Path.of(file.getAbsolutePath()), getReport());
        return file;
    }

    void deleteFile(String path) {
        try {
            Files.delete(Path.of(path));
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    public String getFileUrl() {
        try {
            deleteFile("src/main/resources/static/files/" + this.userName + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File file = createFile();
            if (file != null) {
                try {
                    file = writeFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    return file.getAbsolutePath();
                }
            }
            return "file.getAbsolutePath()";
        }
    }


    @Override
    public String toString() {
        return "u: " + userName + "- messages sent list \n\nid: " + id + "\troles: " + roles + "\n" + "Name: " + firstName + " \n" + "Surname: " + lastName + "\n" + "Email: " + email;
    }


}

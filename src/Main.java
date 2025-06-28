
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();

        // 读取用户文件
        File file = new File("users.csv");
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine(); // 跳过表头
                }
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    User user = User.fromString(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
                System.out.println("📂 已从文件加载 " + users.size() + " 个用户");
            } catch (IOException e) {
                System.out.println("⚠️ 加载用户失败：" + e.getMessage());
            }
        } else {
            System.out.println("📄 文件不存在，将在注册时创建");
        }

        while (true) {
            System.out.println("\n=== 欢迎使用用户系统 ===");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            System.out.print("请选择功能：");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("请输入用户名：");
                String username = scanner.nextLine();
                System.out.print("请输入密码：");
                String password = scanner.nextLine();

                String inputHashed = PasswordUtils.hash(password);
                boolean success = false;
                User currentUser = null;

                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(inputHashed)) {
                        success = true;
                        currentUser = user;
                        break;
                    }
                }

                if (success) {
                    System.out.println("✅ 登录成功，欢迎你，" + currentUser.getUsername());
                    System.out.println("注册时间：" + currentUser.getRegisterTime());

                    if (currentUser.isAdmin()) {
                        // 管理员功能
                        while (true) {
                            System.out.println("\n--- 管理员功能 ---");
                            System.out.println("1. 查看所有用户");
                            System.out.println("2. 删除用户");
                            System.out.println("3. 修改用户信息");
                            System.out.println("4. 返回主菜单");
                            System.out.print("请输入选项：");

                            String adminChoice = scanner.nextLine();

                            if (adminChoice.equals("1")) {
                                System.out.println("📋 当前所有用户：");
                                for (User u : users) {
                                    System.out.println("- 用户名：" + u.getUsername()
                                            + " | 管理员：" + u.isAdmin()
                                            + " | 注册时间：" + u.getRegisterTime());
                                }
                            } else if (adminChoice.equals("2")) {
                                System.out.print("请输入要删除的用户名：");
                                String toDelete = scanner.nextLine();
                                boolean found = false;

                                Iterator<User> iter = users.iterator();
                                while (iter.hasNext()) {
                                    User u = iter.next();
                                    if (u.getUsername().equals(toDelete)) {
                                        iter.remove();
                                        found = true;
                                        System.out.println("✅ 用户 " + toDelete + " 已删除！");
                                        break;
                                    }
                                }

                                if (!found) {
                                    System.out.println("❌ 未找到用户：" + toDelete);
                                } else {
                                    try (FileWriter fw = new FileWriter("users.csv", false)) {
                                        fw.write("username,password,isAdmin,registerTime\n");
                                        for (User u : users) {
                                            fw.write(u.toString() + "\n");
                                        }
                                        System.out.println("📝 用户列表已更新到CSV文件！");
                                    } catch (IOException e) {
                                        System.out.println("⚠️ 文件写入失败：" + e.getMessage());
                                    }
                                }

                            } else if (adminChoice.equals("3")) {
                                System.out.print("请输入要修改的用户名：");
                                String targetUser = scanner.nextLine();
                                boolean found = false;

                                for (User u : users) {
                                    if (u.getUsername().equals(targetUser)) {
                                        found = true;

                                        System.out.print("请输入新密码（留空则不修改）：");
                                        String newPass = scanner.nextLine();
                                        if (!newPass.isEmpty()) {
                                            String hashedPass = PasswordUtils.hash(newPass);
                                            u.setPassword(hashedPass);
                                            System.out.println("🔑 密码已修改");
                                        }

                                        System.out.print("是否设置为管理员？(y/n, 留空不变)：");
                                        String changeAdmin = scanner.nextLine().trim().toLowerCase();
                                        if (changeAdmin.equals("y")) {
                                            u.setAdmin(true);
                                            System.out.println("🎩 已设置为管理员");
                                        } else if (changeAdmin.equals("n")) {
                                            u.setAdmin(false);
                                            System.out.println("🧑 已设置为普通用户");
                                        }

                                        break;
                                    }
                                }

                                if (!found) {
                                    System.out.println("❌ 没有找到该用户！");
                                } else {
                                    // 更新 CSV 文件
                                    try (FileWriter fw = new FileWriter("users.csv", false)) {
                                        fw.write("username,password,isAdmin,registerTime\n");
                                        for (User u : users) {
                                            fw.write(u.toString() + "\n");
                                        }
                                        System.out.println("📝 用户信息已写入 CSV");
                                    } catch (IOException e) {
                                        System.out.println("⚠️ 写入文件失败：" + e.getMessage());
                                    }
                                }
                            } else if (adminChoice.equals("4")) {
                                break;
                            } else {
                                System.out.println("无效选项，请重新输入");
                            }
                        }

                    } else {
                        // 普通用户功能
                        while (true) {
                            System.out.println("\n--- 用户功能 ---");
                            System.out.println("1. 修改密码");
                            System.out.println("2. 登出");
                            System.out.print("请输入选项：");

                            String userChoice = scanner.nextLine();

                            if (userChoice.equals("1")) {
                                System.out.print("请输入旧密码：");
                                String oldPass = scanner.nextLine();
                                if (PasswordUtils.hash(oldPass).equals(currentUser.getPassword())) {
                                    System.out.print("请输入新密码：");
                                    String newPass = scanner.nextLine();
                                    String hashedPass = PasswordUtils.hash(newPass);
                                    currentUser.setPassword(hashedPass);
                                    System.out.println("✅ 密码修改成功！");

                                    try (FileWriter fw = new FileWriter("users.csv", false)) {
                                        fw.write("username,password,isAdmin,registerTime\n");
                                        for (User u : users) {
                                            fw.write(u.toString() + "\n");
                                        }
                                        System.out.println("📝 用户信息已更新到 CSV 文件");
                                    } catch (IOException e) {
                                        System.out.println("⚠️ 文件写入失败：" + e.getMessage());
                                    }

                                } else {
                                    System.out.println("❌ 旧密码错误，修改失败！");
                                }

                            } else if (userChoice.equals("2")) {
                                System.out.println("👋 已登出");
                                break;
                            } else {
                                System.out.println("无效选项，请重新输入！");
                            }
                        }
                    }

                } else {
                    System.out.println("❌ 用户名或密码错误！");
                }

            } else if (choice.equals("2")) {
                // 注册
                System.out.print("请输入新用户名：");
                String newUser = scanner.nextLine();

                boolean exists = false;
                for (User user : users) {
                    if (user.getUsername().equals(newUser)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("❌ 用户名已存在，请换一个！");
                } else {
                    System.out.print("请输入新密码：");
                    String newPass = scanner.nextLine();
                    String hashedPass= PasswordUtils.hash(newPass);

                    System.out.print("是否设置为管理员用户？(y/n)：");
                    String adminChoice = scanner.nextLine().trim().toLowerCase();
                    boolean isAdmin = adminChoice.equals("y");

                    User newUserObj = new User(newUser, hashedPass, isAdmin);
                    users.add(newUserObj);
                    System.out.println("✅ 注册成功！");

                    try (FileWriter fw = new FileWriter("users.csv", true)) {
                        if (file.length() == 0) {
                            fw.write("username,password,isAdmin,registerTime\n"); // 写入表头
                        }
                        fw.write(newUserObj.toString() + "\n");
                        System.out.println("📝 用户信息已保存到CSV文件");
                    } catch (IOException e) {
                        System.out.println("⚠️ 写入文件失败：" + e.getMessage());
                    }
                }

            } else if (choice.equals("3")) {
                System.out.println("👋 再见！");
                break;
            } else {
                System.out.println("❗ 无效的选项，请重新输入");
            }
        }

        scanner.close();
    }
}

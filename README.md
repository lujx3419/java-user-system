# Java Console User System

🎯 A simple user management system built in Java, running in the console.  
Supports user registration, login, administrator permissions, and data persistence with CSV files.

## 🌟 Features

- ✅ User Registration & Login  
- ✅ CSV File Persistence (`users.csv`)  
- ✅ Admin Privileges  
  - View all users  
  - Delete user accounts  
  - Modify other users' passwords or admin status  
- ✅ User Password Update  
- ✅ Object-Oriented Design (`User` class)  
- ✅ Console menu interaction  

## 🏗 Project Structure

```
javaproject/
├── README.md             # ← This file
├── src/
│   ├── users.csv         # User data CSV file
│   ├── Main.java         # Main logic
│   └── User.java         # User data class
```

## 📦 Technologies Used

- Java 17+ (JDK 24 supported)  
- VSCode or IntelliJ IDEA  
- WSL / Linux / Windows friendly  

## 📄 CSV File Format

```csv
username,password,isAdmin,registerTime
admin,123456,true,2025-06-24T10:15:00
testuser,abcdef,false,2025-06-24T10:20:00
```

## 💻 How to Run

Compile and run:

```bash
javac src/*.java
java -cp src Main
```

Or use VSCode to run the `Main.java` file.

## 🧪 Example Output

```text
=== 欢迎使用用户系统 ===
1. 登录
2. 注册
3. 退出
请选择功能：1
请输入用户名：admin
请输入密码：123456
✅ 登录成功，欢迎你，admin！
🎩 你是管理员！

--- 管理员功能 ---
1. 查看所有用户
2. 删除用户
3. 修改用户信息
4. 返回主菜单
```



## 📜 License

This project is licensed under the MIT License.

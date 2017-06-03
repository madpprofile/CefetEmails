# EmailGenerator

The object of this project is to create an algorithm to auto-generate e-mails for all students/professors of CEFET/RJ. It reads a *.csv* file as input and generates an *.csv* file as output containing the generated e-mails. An algorithm to detect collision between emails is implemented, although it's not perfect. In case the collision treatment isn't enough to generate an unique e-mail, it will print a **NULL** where the conflicting e-mail is supposed to be generated.

Usage:   **progname input.csv output.csv @domain**

Example: **emails.exe /home/user/input.csv /home/user/output.csv @example.com**

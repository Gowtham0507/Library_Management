$source = "D:\Library Management System\Library Management System"
$repo = "D:\Library Management System\Library_Management"

cd $repo

# Ensure main branch is clean and up to date
git checkout main
git pull

# User 1: gnvseswar (ch.eswar005@gmail.com)
git checkout -b feature/db-config
Copy-Item "$source\pom.xml" -Destination "$repo\pom.xml" -Force
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\entity"
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\config"
New-Item -ItemType Directory -Force "$repo\src\main\resources"
Copy-Item "$source\src\main\java\com\libraflow\entity\*" -Destination "$repo\src\main\java\com\libraflow\entity\" -Force
Copy-Item "$source\src\main\java\com\libraflow\config\*" -Destination "$repo\src\main\java\com\libraflow\config\" -Force
Copy-Item "$source\src\main\resources\application.properties" -Destination "$repo\src\main\resources\application.properties" -Force
git add .
git -c user.name="gnvseswar" -c user.email="ch.eswar005@gmail.com" commit -m "Setup project configuration, database entities, and properties"

# User 2: asifbaji-shaik (asifbaji_shaik@srmap.edu.in)
git checkout main
git checkout -b feature/backend-services
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\repository"
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\service"
Copy-Item "$source\src\main\java\com\libraflow\repository\*" -Destination "$repo\src\main\java\com\libraflow\repository\" -Force
Copy-Item "$source\src\main\java\com\libraflow\service\*" -Destination "$repo\src\main\java\com\libraflow\service\" -Force
Copy-Item "$source\src\main\java\com\libraflow\LibraFlowApplication.java" -Destination "$repo\src\main\java\com\libraflow\" -Force
git add .
git -c user.name="asifbaji-shaik" -c user.email="asifbaji_shaik@srmap.edu.in" commit -m "Implement data repositories and core business services"

# User 3: Gowtham0507 (saigowtham05peddinti@gmail.com)
git checkout main
git checkout -b feature/api-controllers
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\controller"
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\dto"
New-Item -ItemType Directory -Force "$repo\src\main\java\com\libraflow\exception"
Copy-Item "$source\src\main\java\com\libraflow\controller\*" -Destination "$repo\src\main\java\com\libraflow\controller\" -Force
Copy-Item "$source\src\main\java\com\libraflow\dto\*" -Destination "$repo\src\main\java\com\libraflow\dto\" -Force
Copy-Item "$source\src\main\java\com\libraflow\exception\*" -Destination "$repo\src\main\java\com\libraflow\exception\" -Force
git add .
git -c user.name="Gowtham0507" -c user.email="saigowtham05peddinti@gmail.com" commit -m "Develop REST API controllers, DTOs, and global exception handling"

# User 4: venkatacharan25 (chakkavenkatacharan@gmail.com)
git checkout main
git checkout -b feature/frontend-ui
New-Item -ItemType Directory -Force "$repo\src\main\resources\static"
Copy-Item -Recurse "$source\src\main\resources\static\*" -Destination "$repo\src\main\resources\static\" -Force
Copy-Item "$source\README.md" -Destination "$repo\README.md" -Force -ErrorAction SilentlyContinue
git add .
git -c user.name="venkatacharan25" -c user.email="chakkavenkatacharan@gmail.com" commit -m "Add frontend UI templates, styling, scripts, and documentation"

# Merge all into main
git checkout main
git merge feature/db-config --no-ff -m "Merge pull request #1 from feature/db-config"
git merge feature/backend-services --no-ff -m "Merge pull request #2 from feature/backend-services"
git merge feature/api-controllers --no-ff -m "Merge pull request #3 from feature/api-controllers"
git merge feature/frontend-ui --no-ff -m "Merge pull request #4 from feature/frontend-ui"

Hello this a "Sepet" basic marketing application. This project has 3 sub project this projects "sepet-web", "sepet-api", "sepet-android".

Let's see this 3 sub projects architectures and implementations.

"Sepet-Android" :
Sepet-android working with clean architecture and using this implementations. 
Retrofit, Roomdatabase, Hilt, Jetpack Compose and ViewModel
You can see sepet-android application in this Gif

![](https://github.com/user-attachments/assets/6d815cc9-a808-4224-98b2-3aa1bcdef6b4)

"Sepet-Api" :
Sepet-api working basic mvc architecture and using this implementaions.
Expres.js, Typescript, Bcrypt, Mongoose, Cookie-Parser, Cors, Jwt, Multer and Ua-Parser
If you want to use sepet-api project, some field come empty this field config/TokenConfig.ts -> SECRET_KEY and storage folder
Storage folder store product images if you pull project this folder cannot be created you need a manually create.
Api has 7 endpoint you can see on index.ts

"Sepet-Web" :
Sepet-Web working nuxt and using this implementaions.
Pinia,Vue,VueRouter,yup,Vee-validate,Bootstrap and Google Fonts.
Sepet-web keeps storage api link in the "Common" folder.
I don't know what more to say about for "Sepet-web"

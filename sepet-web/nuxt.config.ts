// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  
  devtools: { enabled: true },

  css:["~/assets/css/main.css"],

  app:{
    head:{
      title : "Sepet",
      link : [
        {
          rel : "stylesheet",
          href : "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css",
          integrity : "sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH",
          crossorigin : "anonymous"
        },
        {
          rel : "preconnect",
          href: "https://fonts.googleapis.com"
        },
        {
          rel : "preconnect",
          href: "https://fonts.gstatic.com",
          crossorigin : "",
        },
        {
          rel : "stylesheet",
          href: "https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap"
        },
        {
          rel : "stylesheet",
          href : "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
        }
      ],

      script : [
        {
          src : "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js",
          integrity : "sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz",
          crossorigin : "anonymous",
          async : true,
          defer : true
        }
      ]
    },
  },
  ssr : true,
  modules: ["@pinia/nuxt",[
    "@vee-validate/nuxt",
    {
      // disable or enable auto imports
      autoImports: true,
      // Use different names for components
      componentNames: {
        Form: 'VeeForm',
        Field: 'VeeField',
        FieldArray: 'VeeFieldArray',
        ErrorMessage: 'VeeErrorMessage',
      },
    },
  ]]
})
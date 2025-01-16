import multer, { diskStorage } from "multer"
import path from "node:path"
import fs from "fs"

const multerService = multer({
    fileFilter(req, file, callback) {
        if(file.mimetype.split("/")[0] !== "image")
            return callback(new Error("Just only supported image"))
        callback(null , true)
    },
    storage: diskStorage({
        filename(req, file, callback) {
            var datetimestamp = Date.now();
            callback(null, file.fieldname + '-' + datetimestamp + '.' + file.originalname.split('.')[file.originalname.split('.').length -1])
        },
        destination: function (req, file, cb) {
            cb(null, 'storage/')
        },
    })
})


export function removeFile(file : string){
    try{
        fs.promises.rm(`${path.resolve()}\\storage\\${file}`) 
    }catch(e){
        console.error(e)
    }
}

export function removeFiles(file : string[]){
    try{
        if(file.length > 0 )
            file.forEach(element => {
                fs.promises.rm(`${path.resolve()}\\storage\\${element}`) 
            });
    }catch(e){
        console.error(e)
    }
}

export function fileExists(file : string) : boolean{
    try{
        fs.promises.rm(`${path.resolve()}\\storage\\${file}`) 
        return true
    }catch(e){
        return false
    }
}


export default multerService
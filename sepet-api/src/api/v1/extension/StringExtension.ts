/*
   This extension convert turkish characters to eng characters
*/

export {};

declare global {
    interface String {
        convertToEng(this : string): string;
        generateQueryName(this : string): string;
    }
}


String.prototype.convertToEng = function(this: string): string {
    const turkishMap: { [key: string]: string } = {
        'ç': 'c', 'Ç': 'C',
        'ğ': 'g', 'Ğ': 'G',
        'ı': 'i', 'I': 'I',
        'i': 'i', 'İ': 'I',
        'ö': 'o', 'Ö': 'O',
        'ş': 's', 'Ş': 'S',
        'ü': 'u', 'Ü': 'U'
    };

    return this.replace(/[çğıiöşüÇĞIİÖŞÜ]/g, (match) => turkishMap[match] || match);
};


String.prototype.generateQueryName = function(this: string): string {
    return this.convertToEng().toLowerCase().trim().replaceAll(" ", "")
};




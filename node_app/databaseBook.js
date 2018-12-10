//Tạo kết nối
var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'librarymanager'
});
//Kết nối
var connect = function (){
    connection.connect(function (err){
        if(!err){
            console.log("Database is connected...Err: " +err);
        }else {
            console.log("Error connecting database...Err: "+err);
        }
    })
}
//Đóng kết nối
var closeDB = function(){
    connection.end(function(err){
        if(!err)console.log("Close db");
    })
}
//Get thông tin trong bảng sách
//URL Test: localhost:3000/listBook
exports.getBook = function (calbackQuery){
    connect();
    connection.query("SELECT * FROM book ", function (err, results, feilds){
        if(!err){
            calbackQuery(results);// nếu không lỗi gọi trả lại kết quả
        }else{
            console.log("Error: "+err);//nếu lỗi thì in ra lỗi
        }
    });
}

//Thêm sách vào database
//URL Test: localhost:3000/addBook?bookId=111111&bookName=NNNNNN&kind=KKKKKK&pH=HHHHHH&author=TTTTTT&price=999999&picPre=&note=HHHHHHHHHHHHHHHH
exports.addBook = function(bookId, bookName, kind, pH, author, price, picPre, note, callBackAdd){
    connect();
    connection.query("INSERT INTO book (bookId, bookName, kind, pH, author, price, picPre, note) VALUES ('"+bookId+"', '"+bookName+"', '"+kind+"', '"+pH+"', '"+author+"', '"+price+"', '"+picPre+"', '"+note+"');",function(err, results, feilds){
        if(!err){
            callBackAdd(results);
        }else{
            console.log("Error: " +err);
        }
    });
}
//Cập nhập sách trong database
//URL Test: localhost:3000/updateBook?bookName=NNNNNN&kind=KKKKKK&pH=HHHHHH&author=TTTTTT&price=999999&picPre=2&note=3&bookId=12321325
exports.updateBook = function(bookId, bookName, kind, pH, author, price, picPre, note, callBackUpdate){
    connect();
    connection.query("UPDATE book SET bookName = '"+bookName+"', kind = '"+kind+"', pH = '"+pH+"', author = '"+author+"', price = '"+price+"', picPre = '"+picPre+"', note = '"+note+"' WHERE book.bookId ="+bookId, function(err, results, feilds){
        if(!err){
            callBackUpdate(results);
        }else{
            console.log("Error: " +err);
        }
    });
}
//Xoá sách trong database
//URL Test: localhost:3000/deleteBook?bookId=12321325
exports.deleteBook = function(bookId, callBackDel){
    connect();
    connection.query("DELETE FROM book WHERE book.bookId ="+bookId, function(err, results, feilds){
        if(!err){
            callBackDel(results);
        }else{
            console.log("Error: " + err);
        }
    });
}
//Tìm sách theo id trong database
//URL Test: localhost:3000/findBookById?bookId=123456
exports.findBookById = function (bookId, callBackFind){
    connect();
    connection.query("SELECT * FROM book WHERE book.bookId = " + bookId, function (err, results, feilds){
        if(!err){
            callBackFind(results);
        }else {
            console.log("Error: " + err);
        }
    });
}
//Tìm sách theo tên trong database
//URL Test: localhost:3000/findBookByName?bookName=Dac%20Nhan%20Tam
exports.findBookByName = function(bookName, callBackFind){
    connect();
    connection.query("SELECT * FROM book WHERE book.bookName LIKE '%"+bookName+"%'", function(err, results, feilds){
        if(!err){
            callBackFind(results);
        }else{
            console.log("Error: " + err);
        }
    });
}
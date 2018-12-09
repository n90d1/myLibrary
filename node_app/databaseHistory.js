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
            console.log("Database is connected...nn");
        }else {
            console.log("Error connecting database...nn");
        }
    })
}
//Đóng kết nối
var closeDB = function(){
    connection.end(function(err){
        if(!err)console.log("Close db");
    })
}
//Get thông tin trong bảng lịch sử
//URL Test: localhost:4000/listHistory
exports.getHistory = function (calbackQuery){
    connect();
    connection.query("SELECT * FROM historyborrow ", function (err, results, feilds){
        if(!err){
            calbackQuery(results);// nếu không lỗi gọi trả lại kết quả
        }else{
            console.log("Error: "+err);//nếu lỗi thì in ra lỗi
        }
    });
}
//Thêm lịch sử vào database
//URL Test: localhost:4000/addHistory?idHistory=null&borrowerId=134679&bookId=8935086838143&dateBorrow=02-12-2018
exports.addHistory = function(borrowerId, bookId, dateBorrow, callBackAdd){
    connect();
    connection.query("INSERT INTO historyborrow (idHistory, borrowerId, bookId, dateBorrow) VALUES ('NULL', '"+borrowerId+"', '"+bookId+"', '"+dateBorrow+"');",function(err, results, feilds){
        if(!err){
            callBackAdd(results);
        }else{
            console.log("Error: " +err);
        }
    });
}
//Xoá sách trong database
//URL Test: localhost:4000/deleteHistory?idHistory=1
exports.deleteHistory = function(idHistory, callBackDel){
    connect();
    connection.query("DELETE FROM historyborrow WHERE historyborrow.idHistory =" +idHistory, function(err, results, feilds){
        if(!err){
            callBackDel(results);
        }else{
            console.log("Error: " + err);
        }
    });
}
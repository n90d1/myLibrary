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
            console.log("Database is connected...Err: "+err);
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
//Get thông tin trong bảng người mượn
//URL Test: localhost:5000/listBorrower
exports.getBorrower = function (calbackQuery){
    connect();
    connection.query("SELECT * FROM borrower ", function (err, results, feilds){
        if(!err){
            calbackQuery(results);// nếu không lỗi gọi trả lại kết quả
        }else{
            console.log("Error: "+err);//nếu lỗi thì in ra lỗi
        }
    });
}
//Thêm người mượn vào database
//URL Test: localhost:5000/addBorrower?borrowerId=111223&picBorrower=3&nameBorrower=Nguyen&birthdayBo=1998-02-05&phoneBo=0922298754&addressBo=15/9@20Ki@20Con&emailBo=nguyenddpd01982@fpt.edu.vn
exports.addBorrower = function(borrowerId, picBorrower, nameBorrower, birthdayBo, phoneBo, addressBo, emailBo, callBackAdd){
    connect();
    connection.query("INSERT INTO borrower (borrowerId, picBorrower, nameBorrower, birthdayBo, phoneBo, addressBo, emailBo) VALUES ('"+borrowerId+"', '"+picBorrower+"', '"+nameBorrower+"', '"+birthdayBo+"', '"+phoneBo+"', '"+addressBo+"', '"+emailBo+"');", function(err, results, feilds){
        if(!err){
            callBackAdd(results);
        }else{
            console.log("Error: " +err);
        }
    });
}
//Cập nhập người mượn trong database
//URL Test: localhost:5000/updateBorrower?picBorrower=3&nameBorrower=Phong&birthdayBo=2000-05-06&phoneBo=0516518191&addressBo=60%20Cao%20Ba%20Quat&emailBo=janguhgnr@gmail.com&borrowerId=111616
exports.updateBorrower = function(borrowerId, picBorrower, nameBorrower, birthdayBo, phoneBo, addressBo, emailBo, callBackUpdate){
    connect();
    connection.query("UPDATE borrower SET picBorrower = '"+picBorrower+"', nameBorrower = '"+nameBorrower+"', birthdayBo = '"+birthdayBo+"', phoneBo = '"+phoneBo+"', addressBo = '"+addressBo+"', emailBo = '"+emailBo+"' WHERE borrower.borrowerId ="+borrowerId, function(err, results, feilds){
        if(!err){
            callBackUpdate(results);
        }else{
            console.log("Error: " +err);
        }
    });
}
//Xoá người mượn trong database
//URL Test: localhost:5000/deleteBorrower?borrowerId=111223
exports.deleteBorrower = function(borrowerId, callBackDel){
    connect();
    connection.query("DELETE FROM borrower WHERE borrower.borrowerId ="+borrowerId, function(err, results, feilds){
        if(!err){
            callBackDel(results);
        }else{
            console.log("Error: " + err);
        }
    });
}
//Tìm người mượn theo id trong database
//URL Test: localhost:5000/findBorrowerById?borrowerId=111616
exports.findBorrowerById = function (borrowerId, callBackFind){
    connect();
    connection.query("SELECT * FROM borrower WHERE borrower.borrowerId = " + borrowerId, function (err, results, feilds){
        if(!err){
            callBackFind(results);
        }else {
            console.log("Error: " + err);
        }
    });
}
//Tìm người mượn theo tên trong database
//URL Test: localhost:5000/findBorrowerByName?nameBorrower=phong
exports.findBorrowerByName = function(nameBorrower, callBackFind){
    connect();
    connection.query("SELECT * FROM borrower WHERE borrower.nameBorrower LIKE '%"+nameBorrower+"%'", function(err, results, feilds){
        if(!err){
            callBackFind(results);
        }else{
            console.log("Error: " + err);
        }
    });
}
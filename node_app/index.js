var express = require('express');

var databaseBo = require ("./databaseBook");//Database cho sách
var databaseBorr = require ("./databaseBorrower");//Database cho người mượn

var app = express();

/////////////////////////////-----BOOK----///////////////////////////////////////////////
//Show list Book
 app.get("/listBook", function (req, res){
   databaseBo.getBook(function(resultQuery){
    res.json(resultQuery);
   });
 });
 app.listen(3000);// Nghe ở port 3000

 //Add Book
 app.get("/addBook", function(req,res){
 	//Tạo biến để truyền các giá trị
   var bookId = req.query.bookId;
   var bookName = req.query.bookName;
   var kind = req.query.kind;
   var pH = req.query.pH;
   var author = req.query.author;
   var price = req.query.price;
   var picPre = req.query.picPre;
   var note = req.query.note;
   //Gọi addBook trong Database và gửi các biến vào 
  databaseBo.addBook(bookId, bookName, kind, pH, author, price, picPre, note, function(resultQuery){
    if(resultQuery.length === 0){// Nếu kết quả trả về có độ dài bằng 0 thì In ra kết quả lỗi 
      var resutNotAdd = {status: -1, text: "Add Failed !"};
      res.json(resutNotAdd);
    }else{
      	var resultOk = {status:1, book: resultQuery};// Ngược lại in trả ra kết quả thêm vào
     	 res.json(resultOk);
    }
  });
 });

//Update Book
app.get("/updateBook", function(req,res){
  	var bookId = req.query.bookId;
	var bookName = req.query.bookName;
	var kind = req.query.kind;
	var pH = req.query.pH;
	var author = req.query.author;
	var price = req.query.price;
	var picPre = req.query.picPre;
	var note = req.query.note;
  databaseBo.updateBook(bookId, bookName, kind, pH, author, price, picPre, note, function(resultQuery){
    if(resultQuery.length === 0){
      	var resutNotUp = {status: -1, text: "Update Failed !"};
      	res.json(resutNotUp);
    }else{
      	var resultOk = {status:1, book: resultQuery};
      	res.json(resultOk);
     }
  });
});

//Delete Book
 app.get("/deleteBook", function(req, res){
   var bookId = req.query.bookId;
   databaseBo.deleteBook(bookId, function(resultQuery){
    if(resultQuery.length === 0){
      var resutNotDel = {status: -1, text: "Delete Failed !"};
      res.json(resutNotDel);
    }else{
      var resultOk = {status:1, book: resultQuery};
      res.json(resultOk);
    }
  });
});

//Find Book => ID
 app.get("/findBookById", function (req, res){
   var bookId = req.query.bookId;
   databaseBo.findBookById(bookId, function (resultQuery){
     if(resultQuery.length === 0){
      	var resutNotFind = {status: -1, text: "Find Id Failed !"};
       	res.json(resutNotFind);
     }else{
     	res.json(resultQuery);
     }
   });
 });

//Find Book => Name
 app.get("/FindBookByname", function(req, res){
   var bookName = req.query.bookName;
   databaseBo.findBookByName(bookName, function (resultQuery){
     if(resultQuery.length === 0){
      	var resutNotFind = {status: -1, text: "Find Name Failed !"};
      	res.json(resutNotFind);
     }else{
      	res.json(resultQuery);
     }
   });
 });
 /////////////////////////////-----BORROWER----///////////////////////////////////////////
 app.get("/listBorrower", function(req, res){
 	databaseBorr.getBorrower(function (resultQuery){
 		res.json(resultQuery);
 	});
 });
 app.listen(5000);// Nghe ở Port 5000

 // Add Borrower
 app.get("/addBorrower", function(req, res){
 	var borrowerId = req.query.borrowerId;
 	var picBorrower = req.query.picBorrower;
 	var nameBorrower = req.query.nameBorrower;
 	var birthdayBo = req.query.birthdayBo;
 	var phoneBo = req.query.phoneBo;
 	var addressBo = req.query.addressBo;
 	var emailBo = req.query.emailBo;
 	databaseBorr.addBorrower(borrowerId, picBorrower, nameBorrower, birthdayBo, phoneBo, addressBo, emailBo, function(resultQuery){
 		if (resultQuery.length == 0) {
 			var resutNotAdd = {status: -1, text: " Add Failed !"};
 			res.json(resutNotAdd);
 		}else{
 			var resultOk = {status: 1, borrower: resultQuery};
 			res.json(resultOk);
 		}
 	});
 });

 //Update Borrower
 app.get("/updateBorrower", function(req, res){
 	var borrowerId = req.query.borrowerId;
 	var picBorrower = req.query.picBorrower;
 	var nameBorrower = req.query.nameBorrower;
 	var birthdayBo = req.query.birthdayBo;
 	var phoneBo = req.query.phoneBo;
 	var addressBo = req.query.addressBo;
 	var emailBo = req.query.emailBo;
 	databaseBorr.updateBorrower(borrowerId, picBorrower, nameBorrower, birthdayBo, phoneBo, addressBo, emailBo, function(resultQuery){
	 	if(resultQuery.length == 0){
	      	var resutNotUp = {status: -1, text: "Update Failed !"};
	      	res.json(resutNotUp);
	    }else{
	      	var resultOk = {status:1, book: resultQuery};
	      	res.json(resultOk);
	    }
 	});
 });

 //Delete Borrower
 app.get("/deleteBorrower", function(req, res){
 	var borrowerId =  req.query.borrowerId;
 	databaseBorr.deleteBorrower(borrowerId, function(resultQuery){
	 	if(resultQuery.length == 0){
	      var resutNotDel = {status: -1, text: "Delete Failed !"};
	      res.json(resutNotDel);
	    }else{
	      var resultOk = {status:1, book: resultQuery};
	      res.json(resultOk);
	    }
 	});
 });

 //Find Borrower => ID
 app.get("/findBorrowerById", function(req, res){
 	var borrowerId =  req.query.borrowerId;
 	databaseBorr.findBorrowerById(borrowerId, function(resultQuery){
 		if(resultQuery.length == 0){
 			var resutNotFind = {status: -1, text: "Find Failed !"};
 			res.json(resutNotFind);
 		}else{
 			res.json(resultQuery);
 		}
 	});
 });

 //Find Borrower => Name
 app.get("/findBorrowerByName", function(req, res){
 	var nameBorrower =  req.query.nameBorrower;
 	databaseBorr.findBorrowerByName(nameBorrower, function(resultQuery){
 		if(resultQuery.length == 0){
 			var resutNotFind = {status: -1, text: "Find Failed !"};
 			res.json(resutNotFind);
 		}else{
 			res.json(resultQuery);
 		}
 	});
 });
 /////////////////////////////-----BOOK----///////////////////////////////////////////////
 /////////////////////////////-----BOOK----///////////////////////////////////////////////
//Program Used to Load Stock data from api into MySQL server
var request = require('request');
var mysql = require('mysql');

var con = mysql.createConnection({
    host: "cs309-jr-4.misc.iastate.edu",
    user: "user",
    password: "Teamjr_4password",
    database: "stock_game",
    port: 3306
});
con.connect(function (err) {
    if (err) throw err;
});

var url = "https://api.iextrading.com/1.0/ref-data/symbols?filter=symbol";
    request.get(url, (error, response, body) => {
        if (error) {
            throw error;
        }
        list = JSON.parse(body);
        list.forEach(item =>{
            var sql = `INSERT INTO apitest2 (symbol) VALUES ("${item.symbol}")`
            con.query(sql, function(err, results){
                if(err) throw err;
            }); 
        });
        con.end();
    });

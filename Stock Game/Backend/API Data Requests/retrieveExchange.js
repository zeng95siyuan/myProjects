//Program Used to Load Exchange data from api into MySQL server
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

var sql = `SELECT Symbol FROM stock2;`;
con.query(sql, function (err, result, fields) {
    if (err) throw err;
    var symbols = result.map(function (value) {
        return value.Symbol;
    });

    var symbolsMat = [];
    for (var x = 50; x < symbols.length; x = x + 50) {
        symbolsMat.push(symbols.slice(x - 50, x));
    }
    for(var x = 0; x < symbolsMath.length;x++){
        
    }
});
function getExchange(symbolsMat) {
    let x = 0;
    var url = `https://api.iextrading.com/1.0/stock/${symbol}/company?filter=exchange`;
    request.get(url, (err, response, body) => {
        if (err) throw err;
        //            console.log(JSON.parse(body).exchange);
        if (body != null) {
            var sqlInsert = `UPDATE stock2 set exchange ='${JSON.parse(body).exchange}' WHERE symbol = '${symbol}'`;
            con.query(sqlInsert, function (err, result, fields) {
                if (err) throw err;
            });
        }

    });
}


/*
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
*/
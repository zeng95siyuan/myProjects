var request = require('request');
var mysql = require('mysql');
var con = mysql.createConnection({
    host: "cs309-jr-4.misc.iastate.edu",
    user: "user",
    password: "Teamjr_4password",
    database: "stock_game",
    port: 3306
});

var stocks = [];

con.connect(function (err) {
    if (err) throw err;
    var sql = "SELECT symbol FROM apitest3;";
    con.query(sql, function (err, result) {
        if (err) throw err;
        result.forEach(item => {
            setTimeout(function () {
                var url = `https://api.iextrading.com/1.0/stock/${item.symbol}/quote?filter=symbol, latestPrice`;
                request.get(url, (error, response, body) => {
                    if (error) {
                        throw error;
                    }
                    var quote = JSON.parse(body);
                    updateTable(quote);
                });
            }, 400);
        });
    });
});

function updateTable(quote) {
    var sql = `UPDATE apitest3 SET price = ${quote.latestPrice} WHERE symbol = "${quote.symbol}"`;
    con.query(sql, function (err) {
        if (err) throw err;
    });
}


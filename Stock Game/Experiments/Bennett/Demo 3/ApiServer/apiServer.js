var express = require('express');
var app = express();
var mysql = require('mysql');
const port = 8081;
var request = require('request');

var con = mysql.createConnection({
    host: "cs309-jr-4.misc.iastate.edu",
    user: "user",
    password: "Teamjr_4password",
    database: "stock_game",
    port: 3306
});



app.get("/stock/:stockId", function (req, res) {
    con.connect(function (err) {
        if (err) throw err;
        var sql = `SELECT symbol FROM apitest3 WHERE id = ${req.params.stockId}`;
        con.query(sql, function (err, result) {
            if (err) throw err;
            //      res.send("Test");
            var url = `https://api.iextrading.com/1.0/stock/${result[0].symbol}/quote?filter=symbol, companyName, latestPrice`;
            request.get(url, (error, response, body) => {
                if (error) {
                    throw error;
                }
                res.send(body);
            });
            con.end();
        });
    });
});

app.listen(port, function () {
    console.log("Listening on port " + port);
});



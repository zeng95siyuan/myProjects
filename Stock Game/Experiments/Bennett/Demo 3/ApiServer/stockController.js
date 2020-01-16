var express = require('express');
var app = express();
var url = require('url');
var mysql = require('mysql');


var con = mysql.createConnection({
        host: "cs309-jr-4.misc.iastate.edu",
        user: "user",
        password: "Teamjr_4password",
        database: "stock_game",
        port: 3306
    });

app.listen(8081, ()=>{
    console.log("Server running on port 8081");
});

app.get("/stocks/:stockId", (req, res) =>{
    con.connect(err => {
        if(err) throw err;
        console.log(req.params.stockId);
        var sql = `SELECT * FROM apitest3 WHERE ID = ${req.params.stockId}`;
        con.query(sql, function(err, result, fields){
            if(err) throw err;
            res.send(JSON.stringify(result));
        });
    });
});
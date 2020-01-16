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

var url = "https://api.iextrading.com/1.0/ref-data/symbols?filter=symbol,name";
request.get(url, (error, response, body) => {
    if (error) {
        throw error;
    }
    list = JSON.parse(body);
    createStatement(list);
    var sql = `INSERT INTO stock (Symbol, Company_Name) VALUES ${list.toString()};`
    con.query(sql, function (err, results) {
        if (err) throw err;
    });
    con.end();
});
function createStatement(list) {
    for (var x = 0; x < list.length; x++) {
        list[x] = "(\"" + list[x].symbol + "\", \"" + list[x].name + "\")";
    }
}
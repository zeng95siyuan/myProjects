var mysql = require('mysql');

exports.database = function () {
    var con = mysql.createConnection({
        host: "cs309-jr-4.misc.iastate.edu",
        user: "user",
        password: "Teamjr_4password",
        database: "stock_game",
        port: 3306
    });
}

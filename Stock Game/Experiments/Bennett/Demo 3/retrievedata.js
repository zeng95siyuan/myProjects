var request = require('request');

var url = "https://api.iextrading.com/1.0/stock/aapl/quote?filter=symbol,companyName, latestPrice";

request.get(url, (error, response, body) =>{
    if(error){
        throw error;
    }
//    console.log(JSON.parse(body));
});


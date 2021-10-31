let error = true
let res = [db.book.drop(),
    db.book.createIndex({"title": 1}, {unique: false}),
    db.book.insert({
        "title": "Lord of The Rings The Fellowship Of The Ring",
        "author": "J.R.R Tolkien",
        "totalPageNumber": 500,
        "price": 60,
        "publishDate": 1635535194,
        "count": 100
    }),
    db.book.insert({
        "title": "Harry Potter The Philosopher's Stone",
        "author": "J.K. Rowling",
        "totalPageNumber": 234,
        "price": 50,
        "publishDate": 1635535194,
        "count": 75
    })]
printjson(res)

if (error) {
    print('Error, exiting')
    quit(1)
}


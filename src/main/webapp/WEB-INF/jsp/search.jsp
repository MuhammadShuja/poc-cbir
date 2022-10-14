<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h2>CBIR Engine (Based on CNN - VGG16)</h2>
        <br/>
        <div class="row">
            <form action="/cbir/search" method="post" enctype="multipart/form-data">
                <p>
                    <label>
                        Min similarity threshold
                        <input type="text" name="min_similarity" placeholder="Between: 0.1 - 1.0"/>
                    </label>
                </p>
                <p>
                    <label>
                        Max products to return
                        <input type="text" name="max_products" placeholder="Between: 10 - 100"/>
                    </label>
                </p>
                <input type="file" name="image" accept="image/*">
                <button type="submit">Search</button>
            </form>
        </div>
    </div>
</body>
</html>

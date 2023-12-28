<ul>
    User
    <li>
        <a href="#user_reg" >User Register</a>
    </li>
    <li>
        <a href="#user_login" >User Login</a>
    </li>
    <li>
        <a href="#otp" >Login and Register Otp</a>
    </li>
    <li>
        <a href="#other_user_api" >Other Api for User</a>
    </li>

</ul>

<ul>
    Other
    <li>
        <a href="#img_crud" >Image CRUD</a>
    </li>
    <li>
        <a href="#product_obj" >Product and Price CRUD</a>
    </li>
    <li>
        <a href="#store_obj" >Store CRUD</a>
    </li>
    <li>
        <a href="#sale_obj" >Sale CRUD</a>
    </li>
    

</ul>
<h3 id="user_reg">User Register</h3>

Register :  http://localhost:8080/api/auth/register <br>
you can get admin role using password (admin123)
Register request obj :

```json
{
  "name": "",
  "gmail": "",
  "password": ""
}
```

Reg Otp  : http://localhost:8080/api/otp/register<br>

<h3 id="user_login">User Login</h3>
Login    : http://localhost:8080/api/auth/authenticate

```json
{
  "gmail": "",
  "password": ""
}
```

Login Otp: http://localhost:8080/api/otp/authenticate
<br>Login request obj :

Login Otp: http://localhost:8080/api/auth/forGetPass

<h3 id="otp">Login and Register Otp </h3>
Otp response obj

```json
{
  "email": "***@gmail",
  "logined": true,
  "checkotp": true,
  "massage": "Successful Login",
  "token": "*****",
  "user": {
    "id": 1,
    "name": "**",
    "user_img": null,
    "password": "$2a$10$9Mz2AkughHATWOGh68L86.lpiWOBS7LNeYBg0nBvE0Ftw21rkZtBy",
    "gmail": "***@gmail",
    "address": "**",
    "role": "ADMIN",
    "enabled": true,
    "accountNonLocked": true,
    "username": "**@gmail",
    "authorities": [
      {
        "authority": "ADMIN"
      }
    ],
    "accountNonExpired": true,
    "credentialsNonExpired": true
  }
}
```

I am using json web token(JWT) authentication.So please store
token value.And then other request (Logined request) doing Bearer token on header

Example in Angular

```
public updateuser(user: any) {
    const tokenStr = 'Bearer ' + sessionStorage.getItem('token');
    const headers= new HttpHeaders().set("Authorization",tokenStr);
    return this.http.post<UserModule>(this.api+"user/update",user,{headers});
}
```

More details example : https://github.com/MyoNaingOo/shopAng/blob/master/src/app/service/api.service.ts
<br>

<h3 id="other_user_api">Other Api for User</h3>
<p style="color:#f80" >It has Logined status</p>

Get User info    : http://localhost:8080/api/v1/user/user (Logined user information)

Get User info By id   : http://localhost:8080/api/v1/user/userid/${id} <br>
Get User info By name   : http://localhost:8080/api/v1/user/username/${name} <br>
Get User info By gmail   : http://localhost:8080/api/v1/user/usergmaill/${gmail} <br>

Get Users info : http://localhost:8080/api/v1/user/users/page/${page_number} <br>
page_number start = 0 // number zero

response obj :

```json
{
  "id": 1,
  "name": "myo",
  "user_img": null,
  "password": null,
  "gmail": "myo@gmail",
  "address": null,
  "role": "USER",
  "enabled": true,
  "username": "myo@gmail",
  "authorities": [
    {
      "authority": "USER"
    }
  ],
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true
}
```

Logout Acc : http://localhost:8080/api/logout GET Method

Delete My Acc : http://localhost:8080/api/v1/user/delete <br>

Delete User : http://localhost:8080/api/v1/user/delete/${id}
(need ADMIN Role)


<p style="color:#ff5" >
Deleted user account doing. other this account has been sale,store and product date is null.
</p>
<br>



<hr>


Using user obj request for this

```json
{
  "id": 1,
  //id can't change
  "name": "**",
  "user_img": null,
  "password": null,
  "gmail": "***@gmail",
  //gmail can't change
  "address": "**",
  "role": "USER"
}
```

Image update: http://localhost:8080/api/v1/user/image
POST request :

```json
{
  "user_img": "***"
  // file name 
}
```

Name update: http://localhost:8080/api/v1/user/changeName
POST request :

```json
{
  "name": "***"
}
```

password update: http://localhost:8080/api/v1/user/changePass
POST request :

```json
{
  "password": "***"
}
```

Address update: http://localhost:8080/api/v1/user/changeAddress
POST request :

```json
{
  "address": "***"
}
```

<h4 id="img_crud" >Image CRUD</h4>
Image Add : http://localhost:8080/api/image/add POST Method <br>
Get Image : http://localhost:8080/api/image/${filename} GET Method <br>
Delete Image : http://localhost:8080/api/image/delete/${filename} DELETE Method <br>
but delete image is auto delete.Product or User account doing delete with image auto delete

<h4 id="product_obj" >Product obj:
</h4>

```json
{
  "id": 1,
  "name": "",
  "img": "",
  "user_id": "",
  "user": "",
  "description": "",
  "balance": 300,
  "price": {
    "product_id": 1,
    "org_price": "",
    "promo_price": "",
    "date": ""
  },
  "time": ""
}

```

Product price obj

```json
{
  "product_id": 1,
  "org_price": "",
  "promo_price": "",
  "date": ""
}
```

Product add     :  http://localhost:8080/api/v1/product/add
request obj

```json
{
  "name": "",
  "img": "",
  "description": ""
}

```

Price add     :  http://localhost:8080/api/v1/price/add
request obj

```json
{
  "product_id": 1,
  "org_price": "",
  "promo_price": ""
}
```


Product delete  :  http://localhost:8080/api/v1/product/delete/${id} id = product_id DELETE/Method<br>
Price delete  :  http://localhost:8080/api/v1/price/delete/${id} id = product_id DELETE/Method<br>
but Product has been sale,store,price data added not available delete <br>

product_details : http://localhost:8080/api/v1/product/${id} id = product_id <br>
response <a href="#product_obj" >Product Obj</a> <br>

products : http://localhost:8080/api/v1/product/products <br>
products find by month : http://localhost:8080/api/v1/product/findByMonth/${num} <br>
products with page : http://localhost:8080/api/v1/product/page/${num} <br>

response array data of product <br>

<h4 id="store_obj" >Store obj:</h4>

```json
{
  "id": "",
  "product": "",
  "user": "",
  "bulk": "",
  "time": "",
  "update_bulk": ""
}
```

Store add : http://localhost:8080/api/v1/store/add <br>
request obj:

```json
{
  "product": 1,
  "bulk": 300
}
```

Stores List: http://localhost:8080/api/v1/store/stores <br>

Stores All List with page: http://localhost:8080/api/v1/store/page/${num} <br>

Stores products balance : http://localhost:8080/api/v1/store/prosBalance/${num} <br>
Stores products Sold out : http://localhost:8080/api/v1/store/prosBalance/${num} <br>
Stores findAll By
Monthly : http://localhost:8080/api/v1/store/findAllByMonth/${num}?month=${month_num}&&year=${year_num} <br>
findAll Stores By Product : http://localhost:8080/api/v1/store/findAllByProduct/${num} <br>
Stores By Product : http://localhost:8080/api/v1/store/findAllByProduct/${num} <br>
Store delete : http://localhost:8080/api/v1/store/delete/${id} id=product_id <br>

<h4 id="sale_obj" >
Sale obj
</h4>

```json
{
  "user": {
    "id": 1,
    "name": "**",
    "user_img": null,
    "password": null,
    "gmail": "***@gmail",
    "address": "**",
    "role": "USER"
  },
  "saleProList": [
    {
      "product_id": 1,
      "bulk": 200
    },
    {
      "product_id": 2,
      "bulk": 200
    }
  ],
  "time": ""
}


```

Sale add : http://localhost:8080/api/v1/sale/add <br>
request obj:

```json
{
  "saleProList": [
    {
      "product_id": 1,
      "bulk": 200
    },
    {
      "product_id": 2,
      "bulk": 200
    }
  ]
}

```

Sale List : http://localhost:8080/api/v1/sale/sales <br>
Sale with page : http://localhost:8080/api/v1/sale/page/${num} <br>
Sale findByMonth : http://localhost:8080/api/v1/sale/findByMonth/${num}?month=${month_num}&&year=${year_num} <br>
Sale delete : http://localhost:8080/api/v1/sale/delete/${num} <br>



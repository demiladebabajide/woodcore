# AccountController

AccountController


---
## createAccount

> BASIC

**Path:** /api/accounts

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Request Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Response Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```




---
## getAccountByNumber

> BASIC

**Path:** /api/accounts/{accountNumber}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| accountNumber |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Response Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```




---
## updateAccountDetails

> BASIC

**Path:** /api/accounts/{accountId}/update

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| accountId |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Request Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Response Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```




---
## activateAccount

> BASIC

**Path:** /api/accounts/{accountId}/activate

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| accountId |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Response Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```




---
## closeAccount

> BASIC

**Path:** /api/accounts/{accountId}/delete

**Method:** PUT

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| accountId |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| accountNumber | integer |  |
| firstName | string |  |
| lastName | string |  |
| address | string |  |
| accountType | string |  |
| accountStatus | string |  |
| balance | number |  |

**Response Demo:**

```json
{
  "accountNumber": 0,
  "firstName": "",
  "lastName": "",
  "address": "",
  "accountType": "",
  "accountStatus": "",
  "balance": 0.0
}
```




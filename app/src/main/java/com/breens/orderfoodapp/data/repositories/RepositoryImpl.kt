package com.breens.orderfoodapp.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_ACCOUNT
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_BANNER
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_CARD
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_CART
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_CATEGORY
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_EVALUATE
import com.breens.orderfoodapp.common.COLLECTION_PATH_NAME_ORDER
import com.breens.orderfoodapp.common.PLEASE_CHECK_INTERNET_CONNECTION
import com.breens.orderfoodapp.common.Result
import com.breens.orderfoodapp.common.convertDateFormat
import com.breens.orderfoodapp.common.getCurrentTimeAsString
import com.breens.orderfoodapp.data.model.Banner
import com.breens.orderfoodapp.data.model.Card
import com.breens.orderfoodapp.data.model.Cart
import com.breens.orderfoodapp.data.model.Cate
import com.breens.orderfoodapp.data.model.Chat
import com.breens.orderfoodapp.data.model.Evaluate
import com.breens.orderfoodapp.data.model.Order
import com.breens.orderfoodapp.data.model.Task
import com.breens.orderfoodapp.data.model.User
import com.breens.orderfoodapp.di.IoDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val foodDB: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : Repository  {
    override suspend fun loginUser(email: String, password: String): Result<List<User>> {
        return try {
            withContext(ioDispatcher) {

                val authResult = try {
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                } catch (e: Exception) {
                    // Handle failed login attempt (e.g., wrong password, no network, etc.)
                    Log.d("ERROR: ", "Login failed: ${e.message}")
                    return@withContext Result.Failure(e)
                }

                // If sign-in was successful, proceed to fetch users
                if (authResult.user != null) {
                    val users = try {
                        foodDB.collection(COLLECTION_PATH_NAME_ACCOUNT)
                            .whereEqualTo("email", email)
                            .get()
                            .await()
                            .documents.map { document ->
                                User(
                                    userID = document.id,
                                    firstName = document.getString("firstName") ?: "",
                                    lastName  = document.getString("lastName") ?: "",
                                    email  = document.getString("email") ?: "",
                                    role  = document.get("role")?.let {
                                        when (it) {
                                            is Long -> it.toInt()
                                            is Double -> it.toInt()
                                            is String -> it.toIntOrNull() ?: 0
                                            else -> 0
                                        }
                                    } ?: 0,
                                )
                            }
                    } catch (e: Exception) {
                        // Handle failed fetching from Firestore
                        Log.d("ERROR: ", "Failed to fetch user data: ${e.message}")
                        return@withContext Result.Failure(e)
                    }
                    Result.Success(users)
                } else {
                    // Handle null user scenario (unlikely in this context but good practice)
                    Result.Failure(IllegalStateException("Unknown error, user is null after successful login"))
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun logoutUser(): Result<Unit>{
        return try {
            withContext(ioDispatcher) {
                val authResult = withTimeoutOrNull(10000L) {
                    firebaseAuth.signOut()
                }

                if (authResult == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    /*.document("8kbrzXslDixuiEKcNpXC") // Giả sử userID được sử dụng làm ID của phần tử mẹ
    .collection("orders") // Giả sử "orders" là tên của collection chứa các đơn hàng
    .add(order)*/
    override suspend fun registerUser(firstName:String, lastName: String,email: String, password: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val addAccountTimeout = withTimeoutOrNull(10000L) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val role = 0
                                val userProfile = hashMapOf(
                                    "firstName" to firstName,
                                    "lastName" to lastName,
                                    "email" to email,
                                    "role" to role
                                )
                                foodDB.collection(COLLECTION_PATH_NAME_ACCOUNT)
                                    .add(userProfile)

                            } else {
                                // Handle sign-up failure

                            }
                        }

                }

                if (addAccountTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun forgetPassword(email: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val resetPasswordTimeout = withTimeoutOrNull(10000L) {
                    firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.e(TAG, "Successfull", task.exception)
                            } else {
                                // Xử lý khi gửi email reset mật khẩu thất bại
                                Log.e(TAG, "Error sending password reset email", task.exception)
                            }
                        }
                }

                if (resetPasswordTimeout == null) {
                    // Xử lý khi timeout
                    Log.e(TAG, "Reset password timeout, please check your internet connection")
                    Result.Failure(IllegalStateException("Reset password timeout, please check your internet connection"))
                } else {
                    // Gửi email reset mật khẩu thành công
                    Result.Success(Unit)
                }
            }
        } catch (exception: Exception) {
            // Xử lý ngoại lệ
            Log.e(TAG, "Error during forget password: $exception")
            Result.Failure(exception)
        }
    }

    /*override suspend fun getAllAccount(): Result<List<User>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingAccountTimeout = withTimeoutOrNull(10000L) {
                        todoChampDB.collection(COLLECTION_PATH_NAME_ACCOUNT)
                            .get()
                            .await()
                            .documents.map { document ->
                                User(
                                    firstName = document.getString("firstName") ?: "",
                                    lastName  = document.getString("lastName") ?: "",
                                    email  = document.getString("email") ?: "",
                                )
                            }

                }

                if (fetchingAccountTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Log.d("BANNERS: ", "${fetchingAccountTimeout?.toList()}")

                Result.Success(fetchingAccountTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }*/
    override suspend fun getAllBanner(): Result<List<Banner>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingBannerTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_BANNER)
                        .get()
                        .await()
                        .documents.map { document ->
                            Banner(
                                bannerId = document.id,
                                imageBanner = document.getString("imageBanner") ?: "",
                                titleBanner = document.getString("titleBanner") ?: "",
                                createdAt = convertDateFormat(
                                    document.getString("createdAtBanner") ?: "",
                                ),
                            )
                   }
                }

                if (fetchingBannerTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Log.d("BANNERS: ", "${fetchingBannerTimeout?.toList()}")

                Result.Success(fetchingBannerTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun getAllCates(): Result<List<Cate>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingCategoryTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_CATEGORY)
                        .get()
                        .await()
                        .documents.map { document ->
                            Cate(
                                cateId = document.id,
                                imageCate = document.getString("imageCate") ?: "",
                                titleCate = document.getString("titleCate") ?: "",
                                createdAt = convertDateFormat(
                                    document.getString("createdAt") ?: "",
                                ),
                            )
                        }
                }

                if (fetchingCategoryTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Log.d("CATEGORIES: ", "${fetchingCategoryTimeout?.toList()}")

                Result.Success(fetchingCategoryTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }

    override suspend fun getAllTasks(query: String): Result<List<Task>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingTasksTimeout = withTimeoutOrNull(10000L) {
                    if (query.isEmpty()) {
                        foodDB.collection(COLLECTION_PATH_NAME)
                            .get()
                            .await()
                            .documents.map { document ->
                                Task(
                                    taskId = document.id,
                                    image = document.getString("image") ?: "",
                                    title = document.getString("title") ?: "",
                                    body = document.getString("body") ?: "",
                                    price = document.get("price")?.let {
                                        when (it) {
                                            is Long -> it.toInt()
                                            is Double -> it.toInt()
                                            is String -> it.toIntOrNull() ?: 0
                                            else -> 0
                                        }
                                    } ?: 0,
                                    createdAt = convertDateFormat(
                                        document.getString("createdAt") ?: "",
                                    ),
                                )
                            }
                    } else {
                        val endQuery = query + "\uf8ff"
                        foodDB.collection(COLLECTION_PATH_NAME)
                            .whereGreaterThanOrEqualTo("title", query)
                            .whereLessThanOrEqualTo("title", endQuery)
                            .get()
                            .await()
                            .documents.map { document ->
                                Task(
                                    taskId = document.id,
                                    image = document.getString("image") ?: "",
                                    title = document.getString("title") ?: "",
                                    body = document.getString("body") ?: "",
                                    price = document.get("price")?.let {
                                        when (it) {
                                            is Long -> it.toInt()
                                            is Double -> it.toInt()
                                            is String -> it.toIntOrNull() ?: 0
                                            else -> 0
                                        }
                                    } ?: 0,
                                    createdAt = convertDateFormat(
                                        document.getString("createdAt") ?: "",
                                    ),
                                )
                            }
                    }
                }

                if (fetchingTasksTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)
                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                } else {
                    Log.d("TASKS: ", "${fetchingTasksTimeout.toList()}")
                    Result.Success(fetchingTasksTimeout.toList() ?: emptyList())
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
        }
    }

    override suspend fun getAllCards(): Result<List<Card>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingTasksTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_CARD)
                        .get()
                        .await()
                        .documents.map { document ->
                            Card(
                                cardId = document.id,
                                imageCard = document.getString("image") ?: "",
                                titleCard = document.getString("title") ?: "",
                                bodyCard = document.getString("body") ?: "",
                                cate = document.getString("cate") ?: "",
                                priceCard = document.get("price")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                favorite = document.get("favorite")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                views = document.get("views")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                sale = document.get("sale")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                createdAt = convertDateFormat(
                                    document.getString("createdAt") ?: "",
                                ),
                            )
                        }
                }

                if (fetchingTasksTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Log.d("CARDS: ", "${fetchingTasksTimeout?.toList()}")

                Result.Success(fetchingTasksTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }

    override suspend fun updateCard(
        image: String,
        title: String,
        body: String,
        price: Int,
        favorite: Int,
        views: Int,
        sale: Int,
        cate:String,
        cardId: String
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val cardUpdate: Map<String, Any> = hashMapOf(
                    "image" to image,
                    "title" to title,
                    "body" to body,
                    "price" to price,
                    "favorite" to favorite,
                    "views" to views,
                    "sale" to sale,
                    "cate" to cate,
                )

                val addStatusTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_CARD)
                        .document(cardId)
                        .update(cardUpdate)
                }

                if (addStatusTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun addCart(
        image: String,
        title: String,
        body: String,
        price: Int,
        foodId: String,
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val statusUpdate = hashMapOf(
                    "image" to image,
                    "title" to title,
                    "price" to price,
                    "createdAt" to getCurrentTimeAsString(),
                )

                val addStatusTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_CART)
                        .add(statusUpdate)
                }

                if (addStatusTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun getAllCarts(): Result<List<Cart>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingTasksTimeout = withTimeoutOrNull(10000L) {

                        foodDB.collection(COLLECTION_PATH_NAME_CART)
                            .get()
                            .await()
                            .documents.map { document ->
                                Cart(
                                    foodId = document.id,
                                    image = document.getString("image") ?: "",
                                    title = document.getString("title") ?: "",
                                    body = document.getString("body") ?: "",
                                    price = document.get("price")?.let {
                                        when (it) {
                                            is Long -> it.toInt()
                                            is Double -> it.toInt()
                                            is String -> it.toIntOrNull() ?: 0
                                            else -> 0
                                        }
                                    } ?: 0,
                                    createdAt = convertDateFormat(
                                        document.getString("createdAt") ?: "",
                                    ),
                                )
                            }
                }

                if (fetchingTasksTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)
                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                } else {
                    Log.d("TASKS: ", "${fetchingTasksTimeout.toList()}")
                    Result.Success(fetchingTasksTimeout.toList() ?: emptyList())
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
        }
    }
    override suspend fun deleteCart(foodId: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_CART)
                        .document(foodId)
                        .delete()
                }

                if (addTaskTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun addOrder(
        userID: String,
        code: String,
        address: String,
        imageOrder: String,
        titleOrder: String,
        price: Int,
        quantity: Int,
        paymentMethods: String,
        total: Int,
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val order = hashMapOf(
                    "userID" to userID,
                    "code" to code,
                    "address" to address,
                    "imageOrder" to imageOrder,
                    "title" to titleOrder,
                    "price" to price,
                    "quantity" to quantity,
                    "paymentMethods" to paymentMethods,
                    "total" to total,
                    "status" to "0",
                    "createdAt" to getCurrentTimeAsString(),
                )

                val addOrderTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_ORDER)
                        .add(order)
                }

                if (addOrderTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }



    override suspend fun getAllOrder(): Result<List<Order>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingOrdersTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_ORDER)
                        .get()
                        .await()
                        .documents.map { document ->
                            Order(
                                orderId = document.id,
                                userID = document.getString("userID") ?: "",
                                code = document.getString("code") ?: "",
                                address = document.getString("address") ?: "",
                                imageOrder = document.getString("imageOrder") ?: "",
                                titleOrder = document.getString("title") ?: "",
                                paymentMethods = document.getString("paymentMethods") ?: "",
                                price = document.get("price")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                quantity = document.get("quantity")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                total = document.get("total")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                createdAt = convertDateFormat(
                                    document.getString("createdAt") ?: "",
                                ),
                                status = document.getString("status") ?: ""
                            )
                        }
                }

                if (fetchingOrdersTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Log.d("ORDERS: ", "${fetchingOrdersTimeout?.toList()}")

                Result.Success(fetchingOrdersTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }
    override suspend fun updateStatus(userID:String, code: String, address: String,imageOrder: String, titleOrder: String,price:  Int , quantity:  Int, paymentMethods: String, total: Int,status: String, orderId: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val statusUpdate: Map<String, Any> = hashMapOf(
                    "userID" to userID,
                    "code" to code,
                    "address" to address,
                    "imageOrder" to imageOrder,
                    "title" to titleOrder,
                    "price" to price,
                    "quantity" to quantity,
                    "paymentMethods" to paymentMethods,
                    "total" to total,
                    "status" to status,
                )

                val addStatusTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_ORDER)
                        .document(orderId)
                        .update(statusUpdate)
                }

                if (addStatusTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }

    override suspend fun addMessage(
        senderID: String,
        message: String,
        direction: Boolean
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val message = hashMapOf(
                    "senderID" to senderID,
                    "message" to message,
                    "direction" to direction,
                    "createdAt" to getCurrentTimeAsString(),
                )

                val addOrderTimeout = withTimeoutOrNull(10000L) {
                    val dbRef = firebaseDatabase.reference.child(senderID)
                    val contactRef = dbRef.child((getCurrentTimeAsString()))
                    contactRef.setValue(message)

                }

                if (addOrderTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }


    override suspend fun getAllMessage(): Result<List<Chat>> {

        return try {
            withContext(ioDispatcher) {
                val fetchingOrdersTimeout = withTimeoutOrNull(10000L) {

                    val contactRefs1 = firebaseDatabase.reference.child("nguyenvu1")
                    val deferred = CompletableDeferred<List<Chat>>()

                    contactRefs1.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val chatList = mutableListOf<Chat>()
                            var chatList1 = mutableListOf<Chat>()
                            dataSnapshot.children.forEach { data ->
                                val chat = Chat(
                                    chatID = data.key ?: "",
                                    senderID = data.child("senderID").value as? String ?: "",
                                    direction = data.child("direction").value as? Boolean?: false,
                                    receiveID = data.child("receiveID").value as? String ?: "",
                                    message = data.child("message").value as? String ?: "",
                                    createdAt = convertDateFormat(data.child("createdAt").value as? String ?: ""),
                                )
                                chatList.add(chat)
//                                 reversedList = chatList.reversed()\

                            }

                            deferred.complete(chatList)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException())
                            deferred.completeExceptionally(error.toException())
                        }
                    })
                    deferred.await()
                }

                if (fetchingOrdersTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)
                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                } else {
                    Log.d("MESSAGES: ", "${fetchingOrdersTimeout}")
                    Result.Success(fetchingOrdersTimeout)
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
        }
    }

    override suspend fun addEvaluate(
        userID: String,
        body: String,
        numberStar: Int,
        foodID: String,
        ID: String
    ): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val evaluate = hashMapOf(
                    "userID" to userID,
                    "body" to body,
                    "numberStar" to numberStar,
                    "foodID" to foodID,
                    "createdAt" to getCurrentTimeAsString(),
                )

                val addTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_EVALUATE)
                        .add(evaluate)
                }

                if (addTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }

    override suspend fun getAllEvaluates(): Result<List<Evaluate>> {
        return try {
            withContext(ioDispatcher) {
                val fetchingTasksTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_EVALUATE)
                        .get()
                        .await()
                        .documents.map { document ->
                            Evaluate(
                                Id = document.id,
                                userID = document.getString("userID") ?: "",
                                foodID = document.getString("foodID") ?: "",
                                body = document.getString("body") ?: "",
                                numberStar = document.get("numberStar")?.let {
                                    when (it) {
                                        is Long -> it.toInt()
                                        is Double -> it.toInt()
                                        is String -> it.toIntOrNull() ?: 0
                                        else -> 0
                                    }
                                } ?: 0,
                                createdAt = convertDateFormat(
                                    document.getString("createdAt") ?: "",
                                ),
                            )
                        }
                }

                if (fetchingTasksTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Log.d("TASKS: ", "${fetchingTasksTimeout?.toList()}")

                Result.Success(fetchingTasksTimeout?.toList() ?: emptyList())
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }

    override suspend fun deleteEvaluate(ID: String): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    foodDB.collection(COLLECTION_PATH_NAME_EVALUATE)
                        .document(ID)
                        .delete()
                }

                if (addTaskTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")

            Result.Failure(exception = exception)
        }
    }



}

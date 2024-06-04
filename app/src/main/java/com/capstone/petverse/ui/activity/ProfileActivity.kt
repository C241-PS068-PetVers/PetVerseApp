import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.capstone.petverse.ui.activity.LoginActivity
import com.capstone.petverse.ui.viewmodel.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileActivity(
    viewModel: SignupViewModel,
    logoutAction: () -> Unit,
    deleteAccountAction: () -> Unit
) {
    val context = LocalContext.current

    // Fetch user info when the profile screen is displayed
//    LaunchedEffect(Unit) {
//        viewModel.fetchUserInfo()
//    }

    // Observe LiveData as State
    val name by viewModel.name.observeAsState("Loading...")
    val username by viewModel.username.observeAsState("Loading...")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Screen")
        Text(text = "Full Name: $name")
        Text(text = "Username: $username")

        // Logout Button
        Button(onClick = { logout(context) }) {
            Text(text = "Logout")
        }

        // Delete Account Button
        Button(onClick = { deleteAccount(context) }) {
            Text(text = "Delete Account")
        }
    }
}

fun navigateToLogin(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}

fun logout(context: Context) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    auth.signOut()
    navigateToLogin(context)
}

fun deleteAccount(context: Context) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val db = FirebaseFirestore.getInstance()

    user?.let { currentUser ->
        val userId = currentUser.uid

        // Delete user data from Firestore
        db.collection("users").document(userId)
            .delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // After user data is deleted from Firestore, delete the user from Authentication
                    currentUser.delete()
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                navigateToLogin(context)
                            } else {
                                Toast.makeText(context, "Failed to delete account from Authentication: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Failed to delete user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}



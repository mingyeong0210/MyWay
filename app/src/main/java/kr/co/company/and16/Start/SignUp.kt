package kr.co.company.and16.Start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.company.and16.R

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var signUpButton: Button
    lateinit var gotoLoginButton: Button
    lateinit var emailEditText: EditText
    lateinit var passWordEditText: EditText
    lateinit var passWordCheckEditText: EditText

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Firebase Auth 초기화
        auth = Firebase.auth

        signUpButton = findViewById(R.id.signUpButton)
        gotoLoginButton = findViewById(R.id.gotoLoginButton)

        // 회원가입
        signUpButton.setOnClickListener{
            signUp()
        }

        // 로그인 창으로
        gotoLoginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun signUp() {
        emailEditText = findViewById(R.id.emailEditText)
        passWordEditText = findViewById(R.id.passWordEditText)
        passWordCheckEditText = findViewById(R.id.passWordCheckEditText)

        val email = emailEditText.text.toString()
        val password = passWordEditText.text.toString()
        val passwordCheck = passWordCheckEditText.text.toString()

        // 이메일이나 패스워드나 패스워드 확인 입력 안했을 때
        if(email.length > 0 && password.length > 0 && passwordCheck.length > 0) {
            if(password.equals(passwordCheck)){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser

                        } else {
                            // 비밀번호 6자리 아니거나, 이메일 형식 틀리는 등
                            if(task.exception != null) {
                                Toast.makeText(this, "이메일 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}
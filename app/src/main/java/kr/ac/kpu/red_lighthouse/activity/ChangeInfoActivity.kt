package kr.ac.kpu.red_lighthouse.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ac.kpu.red_lighthouse.databinding.ActivityChangeInfoBinding
import kr.ac.kpu.red_lighthouse.user.User
import kr.ac.kpu.red_lighthouse.user.UserDao

class ChangeInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChangeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userEmail = binding.userEmail // 사용자 이메일 표시 텍스트 뷰
        val userName = binding.userName   // 사용자 닉네임 표시 텍스트 뷰
        val btnEditNickname = binding.btnEditNickname
        val btnEditPw = binding.btnEditPw
        val prefs = getSharedPreferences("user", 0)
        val userId = prefs.getString("userId"," ").toString()
        val userDao = UserDao()
        //데이터베이스에서 user_nickname,user_email값 가져오기 및 출력
        CoroutineScope(Dispatchers.Main).launch {
            var user: User? = User(userId, "", "", "")
            if (user != null) {
                user = userDao.getDataFromFirebase(user.userId)
                userEmail.text = user?.userEmail
                userName.text = user?.userNickname
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        btnEditNickname.setOnClickListener {
            var intent = Intent(applicationContext, ChangeInfoNicknameActivity::class.java)
            startActivity(intent) }

        btnEditPw.setOnClickListener {
            var intent = Intent(applicationContext, FindPasswordActivity::class.java)
            startActivity(intent) }
    }
}
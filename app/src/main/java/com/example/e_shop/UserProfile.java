package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    private EditText editTextusername,editTextaddress,editTextphoneno;
    private Button buttoncancel , buttonupdate , buttonlogout;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private static int IMAGE_REQUEST = 1 ;
    private Uri image_url ;
    private StorageTask storageTask;
    private StorageReference storageReference ;
    private CircleImageView profilepic ;
    private String propicextension ;
    static int picselected = 0 ;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference("ProfilePictures");

        editTextusername = findViewById(R.id.editTextTextPersonName2);
        editTextaddress = findViewById(R.id.editTextTextPersonName3);
        editTextphoneno = findViewById(R.id.editTextTextPersonName4);
        buttoncancel = findViewById(R.id.button5);
        buttonupdate = findViewById(R.id.button6);

        buttonlogout = findViewById(R.id.button8);
        profilepic = findViewById(R.id.profile_image);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenImage();
                picselected = 1 ;
            }
        });


        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(UserProfile.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
            }
        });



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                editTextusername.setText(user.getUsername());
                editTextaddress.setText(user.getAddress());
                editTextphoneno.setText(user.getPhone());
                propicextension = user.getImageurlext();
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                StorageReference proref = storageReference.child(firebaseUser.getUid()+"."+propicextension);
                proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().placeholder(R.drawable.ic_baseline_account_circle_24).centerCrop().into(profilepic);
                    }
                });

                //Picasso.get().load(user.getImageurl()).fit().placeholder(R.drawable.ic_baseline_image_24).centerCrop().into(profilepic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updated_username = editTextusername.getText().toString();
                String updated_address = editTextaddress.getText().toString();
                String updated_phoneno = editTextphoneno.getText().toString().trim();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("username",updated_username);
                hashMap.put("address",updated_address);
                hashMap.put("phone",updated_phoneno);
                databaseReference.updateChildren(hashMap);

                if (picselected==0)
                    Toast.makeText(getApplicationContext(),"User information updated successfully",Toast.LENGTH_SHORT).show();


                try {
                    if (picselected == 1) {
                        final StorageReference ref = storageReference.child(firebaseUser.getUid() + "." + getfileextension(image_url));
                        final ProgressDialog progressDialog = new ProgressDialog(UserProfile.this);
                        progressDialog.setMessage("Uploading");
                        progressDialog.show();
                        ref.putFile(image_url)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("imageurlext", getfileextension(image_url));
                                        databaseReference.updateChildren(hashMap);
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"User information updated successfully",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                        //Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });

                    }
                }catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"User information updated successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        editTextusername.setText(user.getUsername());
                        editTextaddress.setText(user.getAddress());
                        editTextphoneno.setText(user.getPhone());
                        StorageReference proref = storageReference.child(firebaseUser.getUid()+"."+propicextension);
                        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).fit().placeholder(R.drawable.ic_baseline_account_circle_24).centerCrop().into(profilepic);
                                image_url=null;
                            }
                        });

                        Toast.makeText(getApplicationContext(),"User information restored",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void OpenImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getfileextension(Uri uri)
    {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            image_url = data.getData();
            Picasso.get().load(image_url).fit().centerCrop().into(profilepic);
        }
    }
}
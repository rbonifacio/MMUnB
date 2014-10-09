package br.unb.mobileMedia;

import java.util.ArrayList;
import java.util.List;

import br.unb.mobileMedia.core.db.DBException;
import br.unb.mobileMedia.core.db.DefaultAudioListDAO;
import br.unb.mobileMedia.core.domain.Audio;
import br.unb.mobileMedia.core.manager.Manager;
import br.unb.mobileMedia.core.view.AudioPlayerFragment;
import br.unb.mobileMedia.core.view.AuthorListFragment;
import br.unb.mobileMedia.core.view.ShareListFragment;
import br.unb.mobileMedia.playlist.MainPlaylistListFragment;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MMUnBActivity extends FragmentActivity implements OnItemClickedCallBack{
	
	private AlertDialog alerta;
	
	private void mensagemSincronizarVazio() { 
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Você não tem músicas para executar."); 
		
		builder.setMessage("Gostaria de sincronizar suas músicas?"); 
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			
			public void onClick(DialogInterface arg0, int arg1) { 
				sincronizarTudo();
			} 
		}); 
		
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			
			public void onClick(DialogInterface arg0, int arg1) { 
				//Não quer sincronizar
			}
		}); 
		
		alerta = builder.create(); 
		alerta.show();
		
		
	}
	
	private void sincronizarTudo(){
		
		Toast.makeText(MMUnBActivity.this, "Sincronizar Tudo.", Toast.LENGTH_SHORT).show(); 

	}

	
	@Override
	protected void onCreate(Bundle savedInstanceStace){
		
		super.onCreate(savedInstanceStace);
		setContentView(R.layout.main);
		
		int countAudioInt = 0;
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("MMAndroid"); 
		actionBar.setSubtitle("mobile media");
		
		//Menu com os botoes
		MenuFragment menuFrag = new MenuFragment();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (findViewById(R.id.main)!=null){
			transaction.add(R.id.main, menuFrag);
		}else{
			transaction.add(R.id.menu, menuFrag);
			transaction.add(R.id.content, new ContentFragment());
		}
		
		
		//Possibilidade de sincronizar tudo
		DefaultAudioListDAO countAudio = new DefaultAudioListDAO(getApplicationContext());
		
		try {
			
			countAudioInt = countAudio.countListAudioBanco();
			
			if(countAudioInt == 0){

				mensagemSincronizarVazio();
				
			}
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Fim possibilidade de sincronizar tudo
		
		transaction.commit();
	}

	
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_mmunb_action_bar, menu);
	    return true;
	  }
  
	  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Fragment newFragment = null;
		
		switch(item.getItemId()){
			case R.id.exitActionBar:
				MMUnBActivity.this.finish();
				break;
				
			case R.id.twiiterAction:
				newFragment = new ShareListFragment();
				break;
				
			case R.id.PlayList:
				newFragment = new MainPlaylistListFragment();
				break;
				
		}
		
		
		if (newFragment !=null){
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			
			if(findViewById(R.id.main) != null){
				transaction.replace(R.id.main, newFragment);
				transaction.addToBackStack(null);
			}else{
				transaction.replace(R.id.content, newFragment);
			}
			transaction.commit();
		}
		
		
	    return super.onOptionsItemSelected(item);
	}


	  
	
	public void onItemClicked(int menuItem){
		Fragment newFragment = null;
		switch(menuItem){
			case R.id.btn_list_authors:
				newFragment = new AuthorListFragment();
				break;
				
			case R.id.btn_open_music_player:
				newFragment = new AudioPlayerFragment(); 
				break;

			case R.id.exitActionBar:
				exit();
				break;
				
			case R.id.btn_synchronize:
				try {
					Manager.instance().synchronizeMedia(getApplicationContext());
					Toast.makeText(getApplicationContext(), R.string.message_synchronization_finished, Toast.LENGTH_LONG).show();
				}catch(DBException e) {
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
				break;
				
			default:
				Toast.makeText(getApplicationContext(), R.string.need_to_be_implemented, Toast.LENGTH_LONG).show();
		}
		// TODO Extract this to a method (repeated in AuthorListFragment too)
		if (newFragment !=null){
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			
			if(findViewById(R.id.main) != null){
				transaction.replace(R.id.main, newFragment);
				transaction.addToBackStack(null);
			}else{
				transaction.replace(R.id.content, newFragment);
			}
			transaction.commit();
		}
		
	}

	
	private void exit(){
		try {
			Manager.instance().synchronizeMedia(getApplicationContext());
			Toast.makeText(getApplicationContext(), R.string.message_synchronization_finished, Toast.LENGTH_LONG).show();
		}
		catch(DBException e) {
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		} 
	}


}

package com.vania.alura_agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.vania.alura_agenda.dao.AlunoDAO;

/**
 * Created by vania on 02/11/16.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        byte[] pdu = (byte[]) pdus[0];

        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage smsMessage = SmsMessage.createFromPdu(pdu,formato);

        String telefone = smsMessage.getDisplayOriginatingAddress();
        Log.d("teste", smsMessage.getDisplayMessageBody());

        AlunoDAO dao = new AlunoDAO(context);

        if (dao.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_SHORT).show();
        }

        dao.close();
    }
}

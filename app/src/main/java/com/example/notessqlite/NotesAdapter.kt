import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.notessqlite.Note
import com.example.notessqlite.NotesDatabaseHelper
import com.example.notessqlite.R
import com.example.notessqlite.UpdateNoteActivity

class NotesAdapter(private var notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            showConfirmationDialog(note)
        }
    }

    private fun showConfirmationDialog(note: Note) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirm Delete")
        alertDialogBuilder.setMessage("Are you sure you want to delete this note?")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Do nothing, just dismiss the dialog
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
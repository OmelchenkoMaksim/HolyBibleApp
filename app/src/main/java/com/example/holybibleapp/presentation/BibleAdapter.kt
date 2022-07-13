package com.example.holybibleapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.holybibleapp.R
import com.example.holybibleapp.presentation.BibleAdapter.BibleViewHolder

class BibleAdapter(
    private val retry: Retry,
    private val collapse: CollapseListener,
) : RecyclerView.Adapter<BibleViewHolder>() {

    private val books = ArrayList<BookUi>()

    // метод который будем дергать из юай нашего для обновления элементов
    // ДИФУТИЛ ДОБАВЛЯЕТ АНИМАЦИЮ ПЛАВНУЮ !!!
    fun update(new: List<BookUi>) {
        // создаем дифутил принимает старый список books и новвый new
        val diffCallback = DiffUtilCallback(books, new)
        // высчитывание разницы в списках (принимает созданный дифутилКолбэк)
        val result = DiffUtil.calculateDiff(diffCallback)
        books.clear()
        books.addAll(new)
        // notifyDataSetChanged() не нужен после появления настроенного диффУтила
        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (books[position]) {
            is BookUi.Base -> 0
            is BookUi.Fail -> 1
            is BookUi.Testament -> 2
            is BookUi.Progress -> 3
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> BibleViewHolder.Base(R.layout.book_layout.makeView(parent))
        1 -> BibleViewHolder.Fail(R.layout.fail_fullscreen.makeView(parent), retry = retry)
        2 -> BibleViewHolder.Testament(R.layout.testament.makeView(parent), collapse)
        else -> BibleViewHolder.FullscreenProgress(R.layout.progress_fullscreen.makeView(parent))
    }

    override fun onBindViewHolder(holder: BibleViewHolder, position: Int) =
        holder.bind(books[position])

    override fun getItemCount(): Int = books.size

    abstract class BibleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        open fun bind(book: BookUi) {}

        class FullscreenProgress(view: View) : BibleViewHolder(view)

        // Этот класс нужен что бы убрать дублироване и соответствовать DRY
        // т.к. Base и Testament имеют идентичный код
        abstract class Info(view: View) : BibleViewHolder(view) {
            private val name = itemView.findViewById<TextView>(R.id.textView)
            override fun bind(book: BookUi) {
                book.map(object : BookUi.StringMapper {
                    override fun map(text: String) {
                        name.text = text
                    }
                })
            }
        }

        class Base(view: View) : Info(view)

        class Testament(view: View, private val collapse: CollapseListener) : Info(view) {
            private val collapseView = itemView.findViewById<ImageView>(R.id.collapseView)
            override fun bind(book: BookUi) {
                super.bind(book)
                itemView.setOnClickListener {
                    // collapse.collapseOrExpand(collapse) вот так было бы не корректно,
                    // а правильно вызывать метод элемента
                    book.collapseOrExpand(collapse)
                }
                book.showCollapsed(object : BookUi.CollapseMapper {
                    override fun show(collapse: Boolean) {
                        val iconId = if (collapse) {
                            R.drawable.expand_more
                        } else {
                            R.drawable.expand_less
                        }
                        collapseView.setImageResource(iconId)
                    }
                })
            }
        }

        class Fail(view: View, private val retry: Retry) : BibleViewHolder(view) {

            private val message = itemView.findViewById<TextView>(R.id.messageTextView)
            private val button = itemView.findViewById<Button>(R.id.tryAgainButton)
            override fun bind(book: BookUi) {
                book.map(object : BookUi.StringMapper {
                    override fun map(text: String) {
                        message.text = text
                    }
                })
                button.setOnClickListener { retry.tryAgain() }
            }
        }
    }

    interface Retry {
        fun tryAgain()
    }

    // Interface Segregation о том что мы не добавляем метод в Retry,
    // а пишем новый интерфейс для метода
    interface CollapseListener {
        fun collapseOrExpand(id: Int)
    }
}

private fun Int.makeView(parent: ViewGroup) =
    LayoutInflater.from(parent.context).inflate(this, parent, false)
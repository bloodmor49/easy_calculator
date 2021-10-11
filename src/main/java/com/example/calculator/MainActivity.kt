package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.TextView
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    var lastNumeric : Boolean = false //Для точки. Проверка была ли цмфра.
    var lastDot : Boolean = false //Для точки. Проверка была ли точка


    fun onDigit(view: View){
        //Toast.makeText(this,"Кнопка работает", Toast.LENGTH_SHORT).show()

        tvInput.append((view as Button).text)//добавляем выдаем в окно вывода значение кнопки
        lastNumeric = true

       //if( tvInput.text.contains("1")){ //Если в тексте есть other (значение)
        //   tvInput.text = "Хаха"
       //}

    }

    fun onClear(view: View){

        tvInput.text = "" // заменяем на пустоту
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{ //флаг добавлен ли оператор
        return if (value.startsWith("-")){ //если начинается с -, то впереди минус
            false
        }
        else{ //в остальных случаях тру
            value.contains("/") || value.contains("*")
            || value.contains("+") || value.contains("-") //а значит пустота
        }
    }

    fun onOperator(view: View){
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length - 2)
        }
        return value
    }


    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput.text.toString() //1 - Преобразовываем текст на экране в строку
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){ //если начинается с -
                    prefix = "-"
                    tvValue = tvValue.substring(1) //возвращает всё после 1 символа
                }

                else if(tvValue.contains("-")){ //если есть -
                    val splitValue = tvValue.split("-") //разделяем уравнение по этому знаку
                    var one = splitValue[0] //1 значение - первое число
                    var two = splitValue[1] //2 значение - 2 число
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString()) // суммируем значения и преобразуем в строку
                }

                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*") //разделяем уравнение
                    var one = splitValue[0] //1 значение - первое число
                    var two = splitValue[1] //2 значение - 2 число
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString()) // суммируем значения и преобразуем в строку
                }

                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+") //разделяем уравнение
                    var one = splitValue[0] //1 значение - первое число
                    var two = splitValue[1] //2 значение - 2 число
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString()) // суммируем значения и преобразуем в строку
                }

                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/") //разделяем уравнение
                    var one = splitValue[0] //1 значение - первое число
                    var two = splitValue[1] //2 значение - 2 число
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString()) // суммируем значения и преобразуем в строку
                }

            }catch (e:ArithmeticException){ //Арифметическая ошибка
                e.printStackTrace()
            }
        }

    }
}
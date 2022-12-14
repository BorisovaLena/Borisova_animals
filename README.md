# **Borisova_animals**

Данный проект предназначен для ведения базы учета животных в зоопарке. Приложение предоставляется информацию о названии животного, царстве, типе, классе, отряде, к которым относится данное животное. Также присутствуют фотографии животных. 

## **Начало работы**

Для начала работы необходимо клонировать проект в Android Studio или скачать архив и открыть его с помощью Android Studio. 

### **Необходимые условия**

Для установки приложения необходима программа Android Studio на Вашем компьютере.

### **Установка**

Для установки Вы можете:

1. Клонировать проект в Android Studio:

    * Скопируйте [ссылку на репозиторий](https://github.com/BorisovaLena/Borisova_animals)
    * Запустите Android Studio
    * Нажмите на Get from VCS
    * Вставьте ссылку в поле URL
    * Выберите папку на Вашем компьютере, которая ***не содержит русских букв***
    * Нажмите кнопку Clone
    * Можете приступить к работе
 
 2. Скачать архив с приложением:
 
    * На данной странице нажмите на кнопку Code
    * В раскрывающемся списке выберите Download ZIP
    * Перейдите в загрузки на Вашем компьютере и разархивируйте архив ***(путь к папке не должен содержать русских букв)***
    * Запустите Android Studio
    * Нажмите на Open
    * Найдите проект в открывшемся окне
    * Нажмите OK

## **Работа программы**

![img](https://github.com/BorisovaLena/Borisova_animals/blob/master/anim.png)

В программе есть возможность добавлять, изменять и удалять данные. Для этого был создан интерфейс RetrofitAPI:

```
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("Animals")
    Call<Animal> createPost(@Body Animal dataModal);
    @PUT("Animals")
    Call<Animal> createPut( @Body Animal dataModal, @Query("ID")int id);
    @DELETE("Animals")
    Call<Animal> createDelete(@Query("id") int id);
}
```

## **Автор**

Борисова Лена - [Ссылка на GitHab](https://github.com/BorisovaLena)

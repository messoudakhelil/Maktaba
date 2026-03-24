package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category

class CategoryRepositoryImpl : CategoryRepository {

    private val categoriesList = listOf(
        Category("1", "Programming", "Books about coding and software development"),
        Category("2", "Algorithms", "Data structures and algorithms"),
        Category("3", "Databases", "Database systems and SQL"),
        Category("4", "Networks", "Computer networking and protocols"),
        Category("5", "Cybersecurity", "Security, hacking, and ethical hacking"),
        Category("6", "Artificial Intelligence", "AI, machine learning, and deep learning"),
        Category("7", "Web Development", "Frontend and backend web development"),
        Category("8", "Mobile Development", "Android, iOS, and cross-platform apps"),
        Category("9", "Cloud Computing", "AWS, Azure, GCP, and cloud architecture"),
        Category("10", "Operating Systems", "OS concepts, Linux, Windows"),
        Category("11", "Mathematics", "Discrete math, calculus, linear algebra"),
        Category("12", "Software Engineering", "Design patterns, testing, project management"),
        Category("13", "Data Science", "Data analysis, statistics, visualization")
    )
    override fun getAllCategories(): List<Category> {
        return categoriesList
    }

    override fun getCategoryById(id: String): Category? {
        return categoriesList.find { it.id == id }
    }
}

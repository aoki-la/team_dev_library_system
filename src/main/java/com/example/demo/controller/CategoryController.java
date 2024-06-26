package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Book;
import com.example.demo.entity.CD;
import com.example.demo.entity.Category;
import com.example.demo.entity.CategoryDataForm;
import com.example.demo.entity.DVD;
import com.example.demo.entity.Kamishibai;
import com.example.demo.entity.Room;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.Common;
import com.example.demo.service.LibrarianService;

@Controller
public class CategoryController {
	@Autowired
	LibrarianService librarianService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryRepository categoryRepository;

	//カテゴリ管理
	//home画面
	//Book, CD, DVD, 紙芝居, 会議室
	@GetMapping("/librarian/categories")
	public String categoryEditHome(
			Model model) {

		librarianService.forCategoryList(model);
		librarianService.forLibraryList(model);
		return "categoryEditHome";
	}

	//各カテゴリ詳細画面
	@GetMapping("/librarian/categories/{id}")
	public String categoryEdit(
			@PathVariable("id") String categoryIdStr,
			Model model) {
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects/librarian/home";
		}
		Integer categoryId = Integer.parseInt(categoryIdStr);

		librarianService.forCategoryList(model);
		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryEdit";
	}

	//カテゴリ名前変更画面
	@GetMapping("/librarian/categories/{id}/rename")
	public String categoryRename(
			@PathVariable("id") String categoryIdStr,
			Model model) {
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects/librarian/home";
		}
		Integer categoryId = Integer.parseInt(categoryIdStr);

		librarianService.forCategoryList(model);
		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryRename";
	}

	//カテゴリ名前変更処理
	@PostMapping("/librarian/categories/{id}/rename")
	public String categoryRenameExecute(
			@PathVariable("id") String categoryIdStr,
			@ModelAttribute("renamedCategory") Category category,
			Model model) {
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects/librarian/home";
		}
		Integer categoryId = Integer.parseInt(categoryIdStr);

		categoryRepository.save(category);

		librarianService.forCategoryList(model);
		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryRename";
	}

	//カテゴリーデータ新規登録画面
	@GetMapping("/librarian/categories/{id}/data")
	public String categoryData(
			@PathVariable("id") String categoryIdStr,
			Model model) {
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects:/librarian/home";
		}
		Integer categoryId = Integer.parseInt(categoryIdStr);

		CategoryDataForm categoryDataForm = new CategoryDataForm();
		model.addAttribute("categoryData", categoryDataForm);
		categoryService.forCategoryGenreList(model, categoryId, "genreList");

		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryDataAdd";
	}

	//カテゴリデータ新規登録処理
	@PostMapping("/librarian/categories/{id}/data")
	public String categoryDataAdd(
			@PathVariable("id") String categoryIdStr,
			@RequestParam(name = "libraryId", defaultValue = "1") String libraryIdStr,
			@ModelAttribute("categoryData") CategoryDataForm categoryDataForm,
			Model model) {
		if (!Common.isParceInt(libraryIdStr)) {
			return "redirects:/librarian/home";
		}
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects:/librarian/home";
		}
		Integer libraryId = Integer.parseInt(libraryIdStr);
		Integer categoryId = Integer.parseInt(categoryIdStr);

		categoryService.forCategoryDataStore(model, categoryId, categoryDataForm);

		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		librarianService.forLibraryId(model, libraryId);
		return "categoryEdit";
	}

	//カテゴリデータ一覧画面
	@GetMapping("/librarian/categories/{id}/datalist")
	public String categoryDataList(
			@PathVariable("id") String categoryIdStr,
			Model model) {
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects:/librarian/home";
		}
		Integer categoryId = Integer.parseInt(categoryIdStr);

		categoryService.forCategoryDataList(model, categoryId);

		librarianService.forCategoryList(model);
		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryDataList";
	}

	//カテゴリーデータ更新画面
	@GetMapping("/librarian/categories/{categoryId}/data/{id}")
	public String categoryDataDetail(
			@PathVariable("categoryId") String categoryIdStr,
			@PathVariable("id") String anyIdStr,
			Model model) {

		if (!Common.isParceInt(anyIdStr)) {
			return "redirects:/librarian/home";
		}
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects:/librarian/home";
		}
		Integer anyId = Integer.parseInt(anyIdStr);
		Integer categoryId = Integer.parseInt(categoryIdStr);

		categoryService.forCategoryDataDetail(model, categoryId, anyId, "categoryData");
		categoryService.forCategoryGenreList(model, categoryId, "genreList");

		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryDataEdit";
	}

	//カテゴリーデータ更新処理
	@PostMapping("/librarian/categories/{categoryId}/data/{id}")
	public String categoryDataEdit(
			@PathVariable("categoryId") String categoryIdStr,
			@PathVariable("id") String anyIdStr,
			@ModelAttribute("objectBook") Book book,
			@ModelAttribute("objectCD") CD cd,
			@ModelAttribute("objectDVD") DVD dvd,
			@ModelAttribute("objectKamishibai") Kamishibai kamishibai,
			@ModelAttribute("objectRoom") Room room,
			Model model) {

		if (!Common.isParceInt(anyIdStr)) {
			return "redirects:/librarian/home";
		}
		if (!Common.isParceInt(categoryIdStr)) {
			return "redirects:/librarian/home";
		}
		Integer anyId = Integer.parseInt(anyIdStr);
		Integer categoryId = Integer.parseInt(categoryIdStr);

		//データの直入れがキモい
		categoryService.forCategoryDataEdit(categoryId, book, cd, dvd, kamishibai, room);

		categoryService.forCategoryDataDetail(model, categoryId, anyId, "categoryData");
		categoryService.forCategoryGenreList(model, categoryId, "genreList");

		librarianService.forCategoryId(model, categoryId);
		librarianService.forLibraryList(model);
		return "categoryDataEdit";
	}

}

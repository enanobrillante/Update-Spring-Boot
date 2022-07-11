package com.uploadfiles.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uploadfiles.dao.IUsuarioDao;
import com.uploadfiles.entity.Usuario;

@Controller
public class UsuarioController {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@GetMapping("/")
	public String form(Model model) {
		model.addAttribute("user",new Usuario()); //"user" es el atributo que se usa en th:object="${user}" de la vista form
		model.addAttribute("title","Página de inicio");
		return "form";
	}
	
	@PostMapping("/")
	public String guardar(@RequestParam(name="file_foto",required = false)MultipartFile foto,@Valid Usuario usuario,BindingResult result, RedirectAttributes flash) {
		
		if(result.hasErrors()) {
			return "form"; //si hay un error en algun campo, nos devuelve la vista inicial
		}
		
		else {
			if(!foto.isEmpty()) {
				String ruta = "C://Temp//uploads";//se guarda la foto en un directorio fuera del proyecto
				
				try {
					byte[] bytes = foto.getBytes();
					Path rutaAbsoluta = Paths.get(ruta + "//" + foto.getOriginalFilename());
					Files.write(rutaAbsoluta, bytes);
					usuario.setFoto(foto.getOriginalFilename());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				usuarioDao.save(usuario);
				flash.addFlashAttribute("success", "Foto subida con éxito!");
			}
			
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("user_listar",usuarioDao.findAll());
		return "listar";
	}
}

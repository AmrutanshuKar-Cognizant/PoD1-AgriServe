package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisoryContentRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.request.AdvisoryContentRequestDTO;
import com.cognizant.agriserve.dto.response.AdvisoryContentResponseDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.exception.UnauthorizedActionException;
import com.cognizant.agriserve.service.AdvisoryContentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisoryContentServiceImpl implements AdvisoryContentService {

    private final AdvisoryContentRepository contentRepo;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Override
    public AdvisoryContentResponseDTO saveContent(AdvisoryContentRequestDTO requestDto,Authentication authentication) {
        // 1. Convert DTO to Entity
        User uploader=curuser(authentication);
        if(uploader.getRole()!=User.Role.Admin&&uploader.getRole()!=User.Role.ProgramManager)
            throw new UnauthorizedActionException("Only Administrators or Program Managers can upload advisory content.");
        AdvisoryContent content = modelMapper.map(requestDto, AdvisoryContent.class);

        // 2. Set default server-side values
        content.setStatus("Active");
        content.setUploadedDate(java.time.LocalDateTime.now());
        content.setUploadedBy(uploader);

        // 3. Save to database
        AdvisoryContent saved = contentRepo.save(content);

        // 4. Convert saved Entity back to ResponseDTO
        return modelMapper.map(saved, AdvisoryContentResponseDTO.class);
    }

    @Override
    public List<AdvisoryContentResponseDTO> getAllActiveContent() {
        return contentRepo.findByStatus("Active").stream()
                .map(content -> modelMapper.map(content, AdvisoryContentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteContent(Long id, Authentication authentication) {
        User user = curuser(authentication);
        AdvisoryContent content = contentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advisory Content not found ID: " + id));

        if (user.getRole().equals(User.Role.Admin)) {
            content.setStatus("Inactive");
            contentRepo.save(content);
            return;
        }

        // SAFE CHECK: Check for null before calling .getEmail()
        if (content.getUploadedBy() == null || !content.getUploadedBy().getEmail().equals(user.getEmail())) {
            throw new UnauthorizedActionException("You are not authorized to delete this content.");
        }

        content.setStatus("Inactive");
        contentRepo.save(content);
    }
    private User curuser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedActionException("Authentication required to perform this action.");
        }
        String userEmail = authentication.getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found: " + userEmail));
    }
}
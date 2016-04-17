package com.cybozu.labs.langdetect;

import com.cybozu.labs.langdetect.util.LangProfile;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Language Detector Factory Class
 * <p>
 * This class manages an initialization and constructions of {@link Detector}.
 * <p>
 * Before using language detection library,
 * load profiles with {@link DetectorFactory#loadProfile(String)} method
 * and set initialization parameters.
 * <p>
 * When the language detection,
 * construct Detector instance via {@link DetectorFactory#create()}.
 * See also {@link Detector}'s sample code.
 * <p>
 * <ul>
 * <li>4x faster improvement based on Elmer Garduno's code. Thanks!</li>
 * </ul>
 *
 * @author Nakatani Shuyo
 * @see Detector
 */
public class DetectorFactory {

    private Map<String, double[]> wordLangProbMap = new HashMap<>();
    private List<String> langList = new ArrayList<>();

    private Long seed;

    public DetectorFactory() {
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param profileDirectory profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(String profileDirectory) throws LangDetectException {
        loadProfile(new File(profileDirectory));
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param profileDirectory profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(File profileDirectory) throws LangDetectException {
        File[] listFiles = profileDirectory.listFiles();
        if (listFiles == null) {
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "Not found profile: " + profileDirectory);
        }

        int langSize = listFiles.length, index = 0;
        for (File file : listFiles) {
            if (file.getName().startsWith(".") || !file.isFile()) {
                continue;
            }
            try (InputStream is = new FileInputStream((file))) {
                LangProfile profile = JSON.decode(is, LangProfile.class);
                addProfile(profile, index, langSize);
                ++index;
            } catch (JSONException e) {
                throw new LangDetectException(ErrorCode.FormatError, "profile format error in '" + file.getName() + "'");
            } catch (IOException e) {
                throw new LangDetectException(ErrorCode.FileLoadError, "can't open '" + file.getName() + "'");
            }
        }
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param jsonProfiles profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(List<String> jsonProfiles) throws LangDetectException {
        int langSize = jsonProfiles.size();
        if (langSize < 2) {
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "Need more than 2 profiles");
        }

        int index = 0;

        for (String json : jsonProfiles) {
            try {
                LangProfile profile = JSON.decode(json, LangProfile.class);
                addProfile(profile, index, langSize);
                ++index;
            } catch (JSONException e) {
                throw new LangDetectException(ErrorCode.FormatError, "profile format error");
            }
        }
    }

    /**
     * @param profile
     * @param langSize
     * @param index
     * @throws LangDetectException
     */
    /* package scope */ void addProfile(LangProfile profile, int index, int langSize) throws LangDetectException {
        String lang = profile.getName();
        if (langList.contains(lang)) {
            throw new LangDetectException(ErrorCode.DuplicateLangError, "duplicate the same language profile: " + lang);
        }
        langList.add(lang);
        for (String word : profile.getFreq().keySet()) {
            if (!wordLangProbMap.containsKey(word)) {
                wordLangProbMap.put(word, new double[langSize]);
            }
            int length = word.length();
            if (length >= 1 && length <= 3) {
                double prob = profile.getFreq().get(word).doubleValue() / profile.getNWords()[length - 1];
                wordLangProbMap.get(word)[index] = prob;
            }
        }
    }

    /**
     * Clear loaded language profiles (reinitialization to be available)
     */
    public void clear() {
        langList.clear();
        wordLangProbMap.clear();
    }

    /**
     * Construct Detector instance
     *
     * @return Detector instance
     * @throws LangDetectException
     */
    public Detector create() throws LangDetectException {
        return createDetector();
    }

    /**
     * Construct Detector instance with smoothing parameter
     *
     * @param alpha smoothing parameter (default value = 0.5)
     * @return Detector instance
     * @throws LangDetectException
     */
    public Detector create(double alpha) throws LangDetectException {
        Detector detector = createDetector();
        detector.setAlpha(alpha);
        return detector;
    }

    private Detector createDetector() throws LangDetectException {
        if (langList.isEmpty()) {
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "need to load profiles");
        }
        // Should duplicate the params, but for performance we know they won't be modified
        return new Detector(wordLangProbMap, langList, seed);
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public List<String> getLangList() {
        return Collections.unmodifiableList(langList);
    }
}
